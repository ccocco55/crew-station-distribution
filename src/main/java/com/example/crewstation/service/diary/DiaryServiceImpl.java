package com.example.crewstation.service.diary;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.aop.aspect.annotation.LogStatus;
import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.enumeration.Secret;
import com.example.crewstation.common.enumeration.Type;
import com.example.crewstation.common.exception.DiaryNotFoundException;
import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.domain.diary.country.DiaryCountryVO;
import com.example.crewstation.domain.file.section.FilePostSectionVO;
import com.example.crewstation.dto.country.CountryDTO;
import com.example.crewstation.dto.diary.*;
import com.example.crewstation.dto.file.FileDTO;
import com.example.crewstation.dto.file.section.FilePostSectionDTO;
import com.example.crewstation.dto.file.tag.ImageDTO;
import com.example.crewstation.dto.file.tag.PostDiaryDetailTagDTO;
import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.dto.post.PostDTO;
import com.example.crewstation.dto.post.file.tag.PostFileTagDTO;
import com.example.crewstation.dto.post.section.SectionDTO;
import com.example.crewstation.repository.crew.diary.CrewDiaryDAO;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.repository.diary.country.DiaryCountryDAO;
import com.example.crewstation.repository.diary.diary.path.DiaryDiaryPathDAO;
import com.example.crewstation.repository.file.FileDAO;
import com.example.crewstation.repository.file.section.FilePostSectionDAO;
import com.example.crewstation.repository.like.LikeDAO;
import com.example.crewstation.repository.post.PostDAO;
import com.example.crewstation.repository.post.file.tag.PostFileTagDAO;
import com.example.crewstation.repository.section.SectionDAO;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.service.tag.TagTransactionService;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.DateUtils;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryDAO diaryDAO;
    private final S3Service s3Service;
    private final SectionDAO sectionDAO;
    private final DiaryTransactionService diaryTransactionService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Map<String, Long>> countryRedisTemplate;
    private final LikeDAO likeDAO;
    private final TagTransactionService tagTransactionService;
    private static final Map<String, String> ORDER_TYPE_MAP = Map.of("좋아요순", "diary_like_count", "최신순", "post_id");
    private static final Map<String, String> CATEGORY_MAP = Map.of("crew", "not null", "individual", "null");
    private final DiaryCountryDAO diaryCountryDAO;
    private final DiaryDiaryPathDAO diaryDiaryPathDAO;
    private final PostDAO postDAO;
    private final FileDAO fileDAO;
    private final FilePostSectionDAO filePostSectionDAO;
    private final PostFileTagDAO postFileTagDAO;
    private final CrewDiaryDAO crewDiaryDAO;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DiaryDTO> selectDiaryList(int limit) {
        List<DiaryDTO> diaries = null;
         Object obj = redisTemplate.opsForValue().get("diaries");
        if (obj != null) {
            ObjectMapper mapper = new ObjectMapper();
            diaries = mapper.convertValue(
                    obj,
                    new TypeReference<List<DiaryDTO>>() {}
            );
        }

        if (diaries != null) {
            diaries.forEach(diary -> {
                String filePath = diary.getDiaryFilePath();
//                String presignedUrl = s3Service.getPreSignedUrl(filePath, Duration.ofMinutes(5));
//                if(diary.getMemberFilePath() != null){
//                    diary.setMemberFilePath(s3Service.getPreSignedUrl(diary.getMemberFilePath(), Duration.ofMinutes(5)));
//                }
                diary.setFileCount(sectionDAO.findSectionFileCount(diary.getPostId()));
//
//                log.info("Diary ID={}, 원본 filePath={}, 발급된 presignedUrl={}",
//                        diary, filePath, presignedUrl);
//                diary.setDiaryFilePath(presignedUrl);
            });
            redisTemplate.opsForValue().set("diaries",diaries,Duration.ofMinutes(5));
            log.info("diaries: {}", diaries);
            return diaries;

        }
        return diaryTransactionService.selectDiaryList(limit);
    }

    @Override
    public LikedDiaryCriteriaDTO getDiariesLikedByMemberId(CustomUserDetails customUserDetails, ScrollCriteria criteria) {
        Long memberId = customUserDetails.getId();
        log.info("좋아요 다이어리 조회 - memberId={}, page={}, size={}", memberId, criteria.getPage(), criteria.getSize());

        // 목록 조회
        List<LikedDiaryDTO> diaries = diaryDAO.findDiariesLikedByMemberId(memberId, criteria);

        // S3 이미지 URL 변환
        diaries.forEach(diary -> {
            if (diary.getMainImage() != null) {
                diary.setMainImage(s3Service.getPreSignedUrl(diary.getMainImage(), Duration.ofMinutes(5)));
            }
//            if (diary.getMemberProfileImage() != null) {
//                diary.setMemberProfileImage(s3Service.getPreSignedUrl(diary.getMemberProfileImage(), Duration.ofMinutes(5)));
//            }
        });

        // 전체 개수 (hasMore 계산용)
        int totalCount = diaryDAO.countDiariesLikedByMemberId(memberId);
        criteria.setTotal(totalCount);

        // DTO 조립
        LikedDiaryCriteriaDTO dto = new LikedDiaryCriteriaDTO();
        dto.setLikedDiaryDTOs(diaries);
        dto.setCriteria(criteria);

        return dto;
    }

    //  좋아요 한 다이어리 개수
    @Override
    public int getCountDiariesLikedByMemberId(CustomUserDetails customUserDetails) {
        Long memberId = customUserDetails.getId();
        log.info("memberId: {}", memberId);
        return diaryDAO.countDiariesLikedByMemberId(memberId);
    }

    //  좋아요 취소
    @Override
    @Transactional
    public void cancelLike(CustomUserDetails customUserDetails, Long diaryId) {
        Long memberId = customUserDetails.getId();

        // likeId 조회
        Long likeId = diaryDAO.findLikeId(memberId, diaryId);

        // 알림 먼저 삭제
        if (likeId != null) {
            diaryDAO.deleteLikeAlarmByLikeId(likeId);
        }

        // 좋아요 삭제
        diaryDAO.deleteLike(memberId, diaryId);
    }

    // 댓글 단 다이어리 목록 조회
    @Override
    public ReplyDiaryCriteriaDTO getReplyDiariesByMemberId(CustomUserDetails customUserDetails, ScrollCriteria criteria) {
        Long memberId = customUserDetails.getId();
        log.info("댓글 단 다이어리 조회 - memberId={}, page={}, size={}", memberId, criteria.getPage(), criteria.getSize());

        List<ReplyDiaryDTO> diaries = diaryDAO.findReplyDiariesByMemberId(memberId, criteria);

        //  S3 이미지 URL 변환
        diaries.forEach(diary -> {
            if (diary.getMainImage() != null) {
                diary.setMainImage(s3Service.getPreSignedUrl(diary.getMainImage(), Duration.ofMinutes(5)));
            }
//            if (diary.getMemberProfileImage() != null) {
//                diary.setMemberProfileImage(s3Service.getPreSignedUrl(diary.getMemberProfileImage(), Duration.ofMinutes(5)));
//            }

            //  상대시간 변환
            diary.setRelativeDatetime(DateUtils.toRelativeTime(diary.getCreatedDatetime()));
        });

        // 전체 개수 (hasMore 계산용)
        int totalCount = diaryDAO.countReplyDiariesByMemberId(memberId);
        criteria.setTotal(totalCount);

        // DTO 조립
        ReplyDiaryCriteriaDTO dto = new ReplyDiaryCriteriaDTO();
        dto.setReplyDiaryDTOs(diaries);
        dto.setCriteria(criteria);

        return dto;
    }

    //  내가 댓글 단 일기 개수
    @Override
    public int getCountReplyDiariesByMemberId(CustomUserDetails customUserDetails) {
        Long memberId = customUserDetails.getId();
        log.info("memberId: {}", memberId);
        return diaryDAO.countReplyDiariesByMemberId(memberId);
    }

    @Override
    @LogReturnStatus
    public DiaryCriteriaDTO getDiaries(Search search, CustomUserDetails customUserDetails) {
        DiaryCriteriaDTO dto = new DiaryCriteriaDTO();
        Search newSearch = new Search();
        int page = search.getPage();
        dto.setSearch(search);

        String category = search.getCategory();
        String orderType = search.getOrderType();
        newSearch.setKeyword(search.getKeyword());
        newSearch.setOrderType(ORDER_TYPE_MAP.getOrDefault(orderType, "post_id"));
        newSearch.setCategory(CATEGORY_MAP.getOrDefault(category, ""));
        Criteria criteria = new Criteria(page, diaryDAO.findCountAllByKeyword(newSearch), 3, 3);
        List<DiaryDTO> diaries = diaryDAO.findAllByKeyword(criteria, newSearch);
        diaries.forEach(diary -> {
            if (diary.getMemberFilePath() != null) {
                diary.setMemberFilePath(s3Service.getPreSignedUrl(diary.getMemberFilePath(), Duration.ofMinutes(5)));
            }
            if (diary.getDiaryFilePath() != null) {
                diary.setDiaryFilePath(s3Service.getPreSignedUrl(diary.getDiaryFilePath(), Duration.ofMinutes(5)));
            }
            if (customUserDetails != null) {
                diary.setUserId(customUserDetails.getId());
                log.info("아이ㅣㄷ:{}", customUserDetails.getId());
                diary.setLikeId(likeDAO.isLikeByPostIdAndMemberId(diary));
                log.info("{}::::",diary);
                log.info(":::::::{}",likeDAO.isLikeByPostIdAndMemberId(diary));
            }
//            diary.setUserId(1L); // 임시
//            diary.setLikeId(likeDAO.isLikeByPostIdAndMemberId(diary));
            diary.setFileCount(sectionDAO.findSectionFileCount(diary.getPostId()));
        });
        criteria.setHasMore(diaries.size() > criteria.getRowCount());

        if (criteria.isHasMore()) {
            diaries.remove(diaries.size() - 1);
        }
        dto.setDiaryDTOs(diaries);
        dto.setCriteria(criteria);
        return dto;
    }

    @Override
    public DiaryCriteriaDTO countDiaryImg(Search search, CustomUserDetails customUserDetails) {
        DiaryCriteriaDTO dto = new DiaryCriteriaDTO();
        Search newSearch = new Search();
        int page = search.getPage();
        dto.setSearch(search);

        String category = (search.getCategory() == null) ? "" : search.getCategory();
        String orderType = (search.getOrderType() == null) ? "" : search.getOrderType();

        String orderBy = ORDER_TYPE_MAP.getOrDefault(orderType, "post_id");
        String categoryValue = CATEGORY_MAP.getOrDefault(category, "");

        newSearch.setKeyword(search.getKeyword());
        newSearch.setOrderType(orderBy);
        newSearch.setCategory(categoryValue);

        int totalCount = diaryDAO.findCountAllByKeyword(newSearch);
        Criteria criteria = new Criteria(page, totalCount, 4, 4);

        List<DiaryDTO> diaries = diaryDAO.findAllByKeyword(criteria, newSearch);
        diaries.forEach(diary -> {
            if (diary.getMemberFilePath() != null) {
                diary.setMemberFilePath(s3Service.getPreSignedUrl(diary.getMemberFilePath(), Duration.ofMinutes(5)));
            }
            if (diary.getDiaryFilePath() != null) {
                diary.setDiaryFilePath(s3Service.getPreSignedUrl(diary.getDiaryFilePath(), Duration.ofMinutes(5)));
            }
            if (customUserDetails != null) {
                diary.setUserId(customUserDetails.getId());
                diary.setLikeId(likeDAO.isLikeByPostIdAndMemberId(diary));
            }

            diary.setFileCount(sectionDAO.findSectionFileCount(diary.getPostId()));
        });
        criteria.setHasMore(diaries.size() > criteria.getRowCount());

        if (criteria.isHasMore()) {
            diaries.remove(diaries.size() - 1);
        }
        dto.setDiaryDTOs(diaries);
        dto.setCriteria(criteria);
        dto.setTotalCount(totalCount);



        return dto;
    }


    @Transactional(rollbackFor = Exception.class)
    @LogStatus
    public void update(PostDiaryDetailTagDTO request) {

        FileDTO fileDTO = new FileDTO();
        FilePostSectionDTO sectionFileDTO = new FilePostSectionDTO();
        Map<String, Long> cached = countryRedisTemplate.opsForValue().get("country::countries");
        PostDTO post = new PostDTO();
//      썸네일이 양수이고 뉴 썸네일이 음수이면 기존이미지 다 삭제
//      썸네일이 양수이고 뉴 썸네이일이 양수이면 기존 이미지에서 썸네일
        final boolean[] check = {request.getThumbnail() > 0 && request.getNewThumbnail() < 0};
        List<Long> countryIds = null;
        List<ImageDTO> images = request.getImages();
        String[] arCountry = null;
        List<DiaryCountryVO> diaryCountryVOs = null;
        List<PostFileTagDTO> postFileTagDTOs = null;


//        post.setPostTitle(request.getPostTitle());
//        post.setMemberId(request.getMemberId());
//        post.setSecret(request.isSecret() ? Secret.PRIVATE : Secret.PUBLIC);
        diaryDAO.updateSecret(request.getPostId(), request.isSecret() ? Secret.PRIVATE : Secret.PUBLIC);
        log.info("{}",toPostVO(request));
        postDAO.update(toPostVO(request));
        log.info("{}", cached);
        if (cached == null) {
            cached = tagTransactionService.getCountries();
        }
        if(request.getCountries() !=null){
            arCountry = request.getCountries();
            countryIds = Arrays.stream(arCountry)
                    .map(cached::get)
                    .collect(Collectors.toList());
            request.setCountryIds(countryIds);
            diaryCountryVOs = toDiaryCountryVO(request);
            diaryCountryVOs.forEach(diaryCountryDAO::save);
        }
//        postDAO.savePost(post);
//        request.setPostId(post.getPostId());
//        diaryDAO.save(toDiaryVO(post));
//        diaryDiaryPathDAO.save(toDiaryDiaryPathVO(request));
//        if (request.getCrewId() != null) {
//            crewDiaryDAO.save(CrewDiaryVO.builder().diaryId(request.getPostId()).crewId(request.getCrewId()).build());
//        }
        Optional.ofNullable(images).orElse(Collections.emptyList()).forEach(image -> {
            MultipartFile file = image.getImage();
            log.info(":::::::::::::{}", image.toString());
            if ((file == null || Objects.equals(file.getOriginalFilename(), "")) && (image.getPostContent() == null || image.getPostContent().isEmpty())) {
                log.info("다 여기로 오니?");
                return;
            }
            try {
//              이미지 추가시 발생
                if (file != null && !Objects.equals(file.getOriginalFilename(), "")) {
                    log.info("어디로 들어오니1");
                    Type type = Type.SUB;
                    if (check[0]) {
                        type = Type.MAIN;
                        check[0] = false;
                    }
                    String s3Key = s3Service.uploadPostFile(file, getPath());
                    String originalFileName = file.getOriginalFilename();
                    String extension = "";
                    if (originalFileName != null && originalFileName.contains(".")) {
                        extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    }
                    fileDTO.setFileName(UUID.randomUUID() + extension);
                    fileDTO.setFilePath(s3Key);
                    fileDTO.setFileSize(String.valueOf(file.getSize()));
                    fileDTO.setFileOriginName(originalFileName);
                    fileDAO.saveFile(fileDTO);
                    image.setPostId(request.getPostId());
                    sectionDAO.saveDiary(image);
                    sectionFileDTO.setFileId(fileDTO.getId());
                    sectionFileDTO.setImageType(type);
                    sectionFileDTO.setPostSectionId(image.getPostSectionId());
                    FilePostSectionVO vo = toSectionFileVO(sectionFileDTO);
                    filePostSectionDAO.save(vo);
                    if (image.getTags() != null) {
                        log.info("태그가 없어");
                        image.getTags().forEach((tag) -> {
                            tag.setPostSectionFileId(fileDTO.getId());
                            postFileTagDAO.save(toPostFileTagVO(tag));
                        });
                    }

                } else {
                    log.info("어디로 들어오니2");
                    image.setPostId(post.getPostId());
                    sectionDAO.saveDiary(image);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        //             아예 삭제 시
        if (request.getDeleteImages() != null) {
            Arrays.stream(request.getDeleteImages()).forEach(id -> {
                postFileTagDAO.deleteAllByFileId(id);
                filePostSectionDAO.deleteByFileId(id);
                fileDAO.delete(id);
            });
        }

        if (request.getDeleteCountries() != null) {
            Arrays.stream(request.getDeleteCountries()).forEach(diaryCountryDAO::delete);
        }

        if (request.getDeleteTags() != null) {
            Arrays.stream(request.getDeleteTags()).forEach(postFileTagDAO::deleteById);
            log.info("태그 삭제됩니다");
        }

        if (request.getDeleteSections() != null) {
            Arrays.stream(request.getDeleteSections()).forEach(sectionDAO::delete);
        }

        Optional.ofNullable(request.getOldImages()).orElse(Collections.emptyList())
                .forEach(image -> {
//                    image.getPostSectionId()
            sectionDAO.update(toPostSectionVO(image));
            if (image.getTags() != null) {
//                log.info("태그가 없어");
                image.getTags().forEach((tag) -> {
                    log.info("태그 추가되빈다.");
                    tag.setPostSectionFileId(image.getFileId());
                    postFileTagDAO.save(toPostFileTagVO(tag));
                });
            }
        });
        if (request.getThumbnail() > 0 && request.getNewThumbnail() > 0) {
            log.info("기존 :::{}",request.getThumbnail());
            log.info("신규 ::::{}",request.getNewThumbnail());
            filePostSectionDAO.updateImageTypeByFileId(request.getThumbnail(), Type.SUB);
            filePostSectionDAO.updateImageTypeByFileId(request.getNewThumbnail(), Type.MAIN);
        }
        if(redisTemplate.opsForValue().get("diaries") != null){
            redisTemplate.delete("diaries");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogStatus
    public void write(PostDiaryDetailTagDTO request) {
        FileDTO fileDTO = new FileDTO();
        FilePostSectionDTO sectionFileDTO = new FilePostSectionDTO();
        Map<String, Long> cached = countryRedisTemplate.opsForValue().get("country::countries");
        PostDTO post = new PostDTO();
        AtomicBoolean check = new AtomicBoolean(false);
        List<Long> countryIds = null;
        List<ImageDTO> images = request.getImages();
        String[] arCountry = null;
        List<DiaryCountryVO> diaryCountryVOs = null;
        List<PostFileTagDTO> postFileTagDTOs = null;


        post.setPostTitle(request.getPostTitle());
        post.setMemberId(request.getMemberId());
        post.setSecret(request.isSecret() ? Secret.PRIVATE : Secret.PUBLIC);
        log.info("{}", cached);
        if (cached == null) {
            cached = tagTransactionService.getCountries();
        }
        arCountry = request.getCountries();
        countryIds = Arrays.stream(arCountry)
                .map(cached::get)
                .collect(Collectors.toList());
        request.setCountryIds(countryIds);
        postDAO.savePost(post);
        log.info("post: {}", post);
        request.setPostId(post.getPostId());
        diaryDAO.save(toDiaryVO(post));
        diaryCountryVOs = toDiaryCountryVO(request);
        diaryCountryVOs.forEach(diaryCountryDAO::save);

//        if (request.getCrewId() != null) {
//            crewDiaryDAO.save(CrewDiaryVO.builder().diaryId(request.getPostId()).crewId(request.getCrewId()).build());
//        }else{
//            diaryDiaryPathDAO.save(toDiaryDiaryPathVO(request));
//        }
        images.forEach(image -> {
            MultipartFile file = image.getImage();
            log.info(":::::::::::::{}", image.toString());
            if ((file == null || Objects.equals(file.getOriginalFilename(), "")) && (image.getPostContent() == null || image.getPostContent().isEmpty())) {
                log.info("다 여기로 오니?");
                return;
            }
            try {
                if (file != null && !Objects.equals(file.getOriginalFilename(), "")) {
                    log.info("어디로 들어오니1");
                    Type type = Type.SUB;
                    if (!check.get()) {
                        type = Type.MAIN;
                        check.set(true);
                    }
                    String s3Key = s3Service.uploadPostFile(file, getPath());
                    String originalFileName = file.getOriginalFilename();
                    String extension = "";
                    if (originalFileName != null && originalFileName.contains(".")) {
                        extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    }
                    fileDTO.setFileName(UUID.randomUUID() + extension);
                    fileDTO.setFilePath(s3Key);
                    fileDTO.setFileSize(String.valueOf(file.getSize()));
                    fileDTO.setFileOriginName(originalFileName);
                    fileDAO.saveFile(fileDTO);
                    image.setPostId(post.getPostId());
                    sectionDAO.saveDiary(image);
                    sectionFileDTO.setFileId(fileDTO.getId());
                    sectionFileDTO.setImageType(type);
                    sectionFileDTO.setPostSectionId(image.getPostSectionId());
                    FilePostSectionVO vo = toSectionFileVO(sectionFileDTO);
                    filePostSectionDAO.save(vo);
                    if (image.getTags() != null) {
                        log.info("태그가 없어");
                        image.getTags().forEach((tag) -> {
                            tag.setPostSectionFileId(fileDTO.getId());
                            postFileTagDAO.save(toPostFileTagVO(tag));
                        });
                    }

                } else {
                    log.info("어디로 들어오니2");
                    image.setPostId(post.getPostId());
                    sectionDAO.saveDiary(image);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        if(redisTemplate.opsForValue().get("diaries") != null){
            redisTemplate.delete("diaries");
        }

    }

    @Override
    @LogReturnStatus
    @Transactional(rollbackFor = Exception.class)
    public DiaryDetailDTO getDiary(Long postId, CustomUserDetails customUserDetails) {
        DiaryDetailDTO diaryDetailDTO = new DiaryDetailDTO();
        postDAO.updateReadCount(postId);
        List<CountryDTO> countries = diaryCountryDAO.findCountryByPostId(postId);
        Optional<DiaryDTO> byPostId = diaryDAO.findByPostId(postId);
        List<SectionDTO> sections = sectionDAO.findSectionsByPostId(postId);
        byPostId.ifPresent(diaryDTO -> {
            if(diaryDTO.getMemberFilePath() != null){
                diaryDTO.setMemberFilePath(s3Service.getPreSignedUrl(diaryDTO.getMemberFilePath(), Duration.ofMinutes(5)));
            }
            diaryDTO.setRelativeDate(DateUtils.toRelativeTime(diaryDTO.getCreatedDatetime()));

            if (customUserDetails != null) {
                diaryDTO.setUserId(Objects.equals(customUserDetails.getId(), diaryDTO.getMemberId()) ? customUserDetails.getId() : null);
                Long likeId = likeDAO.isLikeByPostIdAndMemberId(diaryDTO);
                diaryDTO.setLikeId(likeId);
            }
            log.info("유저 아이디{}",diaryDTO.getUserId());
        });
        diaryDetailDTO.setCountries(countries);
        sections.forEach(section -> {
            log.info("{}", section.getFileId());
            List<PostFileTagDTO> tags = postFileTagDAO.findByFileId(section.getFileId());
            log.info("{}:::::::::::::::::::::::::", tags);
            section.setTags(tags);
            if (section.getFilePath() != null) {
                section.setFilePath(s3Service.getPreSignedUrl(section.getFilePath(), Duration.ofMinutes(5)));
            }
            tags.forEach((tag) -> {
                log.info("tag가 존재하나 :{}", tag);
                if (tag.getFilePath() != null) {
                    tag.setFilePath(s3Service.getPreSignedUrl(tag.getFilePath(), Duration.ofMinutes(5)));
                }
            });

        });

        diaryDetailDTO.setDiary(byPostId.orElseThrow(DiaryNotFoundException::new));
        diaryDetailDTO.setSections(sections);
        return diaryDetailDTO;
    }

    @Override
    @LogReturnStatus
    public String changeSecret(Long diaryId,boolean check) {
        Secret secret = check ? Secret.PRIVATE : Secret.PUBLIC;
        String message = check ? "비공개 설정되었습니다." : "공개 설정되었습니다.";

        if (!postDAO.isActivePost(diaryId)) {
            throw new PostNotFoundException("이미 삭제된 게시글입니다.");
        }
        diaryDAO.updateSecret(diaryId, secret);
        if(redisTemplate.opsForValue().get("diaries") != null){
            redisTemplate.delete("diaries");
        }
        return message;
    }

    @Override
    public List<DiaryDTO> findDiaryById(Long diaryId) {
        List<DiaryDTO>  diaryDTOs = diaryDAO.findDiaryAllByMemberId(diaryId);
        diaryDTOs.forEach( diaryDTO -> {
            diaryDTO.setDiaryFilePath(s3Service.getPreSignedUrl(diaryDTO.getDiaryFilePath(), Duration.ofMinutes(10)));
        });
        return diaryDTOs;
    }

    public void deleteDiary(Long postId) {
        postDAO.updatePostStatus(postId);
        if(redisTemplate.opsForValue().get("diaries") != null){
            redisTemplate.delete("diaries");
        }
    }

    public String getPath() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return today.format(formatter);
    }
}
