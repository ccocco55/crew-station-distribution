package com.example.crewstation.service.member;

import aj.org.objectweb.asm.TypeReference;
import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.common.exception.MemberLoginFailException;
import com.example.crewstation.common.exception.MemberNotFoundException;
import com.example.crewstation.domain.file.FileVO;
import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.dto.file.FileDTO;
import com.example.crewstation.dto.file.member.MemberFileDTO;
import com.example.crewstation.dto.member.*;
import com.example.crewstation.mapper.payment.status.PaymentStatusMapper;
import com.example.crewstation.repository.country.CountryDAO;
import com.example.crewstation.repository.crew.CrewDAO;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.repository.file.FileDAO;
import com.example.crewstation.repository.member.AddressDAO;
import com.example.crewstation.repository.member.MemberDAO;
import com.example.crewstation.repository.member.MemberFileDAO;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberDAO memberDAO;
    private final MemberFileDAO memberFileDAO;
    private final AddressDAO addressDAO;
    private final FileDAO fileDAO;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;
    private final PaymentStatusMapper paymentStatusMapper;
    private final MemberDTO memberDTO;
    private final CrewDAO crewDAO;
    private final CountryDAO countryDAO;
    private final DiaryDAO diaryDAO;
    private final RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void join(MemberDTO memberDTO, MultipartFile multipartFile) {
        memberDTO.setMemberPassword(passwordEncoder.encode(memberDTO.getMemberPassword()));

        MemberVO vo = toVO(memberDTO);
        memberDAO.save(vo);

        Long memberId = vo.getId();

        AddressDTO addressDTO = new AddressDTO();


        log.info("memberId: {}",memberId);

        addressDTO.setMemberId(memberId);
        addressDTO.setAddressDetail(memberDTO.getAddressDTO().getAddressDetail());
        addressDTO.setAddress(memberDTO.getAddressDTO().getAddress());
        addressDTO.setAddressZipCode(memberDTO.getAddressDTO().getAddressZipCode());

        addressDAO.save(toVO(addressDTO));

        if(multipartFile.getOriginalFilename().equals("")){
            return;
        }
        FileDTO fileDTO = new FileDTO();
        MemberFileDTO memberFileDTO = new MemberFileDTO();
        try {
            String s3Key = s3Service.uploadPostFile(multipartFile, getPath());

            String originalFileName = multipartFile.getOriginalFilename();
            String extension = "";

            if(originalFileName != null && originalFileName.contains(".")){
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }


            fileDTO.setFileOriginName(multipartFile.getOriginalFilename());
            fileDTO.setFilePath(s3Key);
            fileDTO.setFileSize(String.valueOf(multipartFile.getSize()));
            fileDTO.setFileName(UUID.randomUUID() + extension);

            FileVO filevo = toVO(fileDTO);
            fileDAO.save(filevo);

            Long fileId = filevo.getId();

            memberFileDTO.setMemberId(memberId);
            memberFileDTO.setFileId(fileId);

            memberFileDAO.save(toVO(memberFileDTO));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean checkEmail(String memberEmail) {
        return memberDAO.checkEmail(memberEmail);
    }

    @Override
    public MemberDTO login(MemberDTO memberDTO) {
        log.info("memberDTO: {}", memberDTO);
        return memberDAO.findForLogin(memberDTO).orElseThrow(MemberLoginFailException::new);
    }

    public String getPath() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return today.format(formatter);
    }

    @Override
    @Cacheable(value="member", key="#memberEmail")
    public MemberDTO getMember(String memberEmail, String provider) {
        return (provider == null ? memberDAO.findByMemberEmail(memberEmail)
                : memberDAO.findBySnsEmail(memberEmail)).orElseThrow(MemberNotFoundException::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void joinSns(MemberDTO memberDTO, MultipartFile multipartFile) {
        MemberVO vo = toVO(memberDTO);
        memberDAO.saveSns(vo);

        Long memberId = vo.getId();

        AddressDTO addressDTO = new AddressDTO();


        log.info("memberId: {}", memberId);

        addressDTO.setMemberId(memberId);
        addressDTO.setAddressDetail(memberDTO.getAddressDTO().getAddressDetail());
        addressDTO.setAddress(memberDTO.getAddressDTO().getAddress());
        addressDTO.setAddressZipCode(memberDTO.getAddressDTO().getAddressZipCode());

        addressDAO.save(toVO(addressDTO));

        if (multipartFile.getOriginalFilename().equals("")) {
            return;
        }
        FileDTO fileDTO = new FileDTO();
        MemberFileDTO memberFileDTO = new MemberFileDTO();
        try {
            String s3Key = s3Service.uploadPostFile(multipartFile, getPath());

            String originalFileName = multipartFile.getOriginalFilename();
            String extension = "";

            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }


            fileDTO.setFileOriginName(multipartFile.getOriginalFilename());
            fileDTO.setFilePath(s3Key);
            fileDTO.setFileSize(String.valueOf(multipartFile.getSize()));
            fileDTO.setFileName(UUID.randomUUID() + extension);

            FileVO filevo = toVO(fileDTO);
            fileDAO.save(filevo);

            Long fileId = filevo.getId();

            memberFileDTO.setMemberId(memberId);
            memberFileDTO.setFileId(fileId);

            memberFileDAO.save(toVO(memberFileDTO));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        memberDAO.saveSns(toVO(memberDTO));

    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<MemberDTO> getMemberProfile (Long memberId) {
        Optional<MemberDTO> result = memberDAO.selectProfileById(memberId);

        result.ifPresent(members -> {
           if (members.getFilePath() != null) {
               String filePath = members.getFilePath();
               members.setFilePath(s3Service.getPreSignedUrl(filePath, Duration.ofMinutes(5)));

           }
        });

        return result;
    }

    @Override
    public void resetPassword(String memberEmail, String memberPassword) {
        String newPassword = passwordEncoder.encode(memberPassword);

        memberDAO.updatePassword(memberEmail, newPassword);
    }

    @Override
    public List<MemberDTO> searchMember(String search) {
        List<MemberDTO> searchMember = memberDAO.findSearchMember(search);
        searchMember.forEach(member->{
            if(member.getFilePath() != null) {
                member.setFilePath(s3Service.getPreSignedUrl(member.getFilePath(), Duration.ofMinutes(5)));
            }

        });
//        return memberDAO.findSearchMember(search);
        return searchMember;
    }
// 관리자 회원 목록
    @Override
    public MemberCriteriaDTO getMembers(Search search) {
        int page = Optional.ofNullable(search.getPage()).orElse(1);
        int size = 10;
        int offset = (page - 1) * size;

        int total = memberDAO.countAdminMembers(search);
        List<MemberDTO> members = memberDAO.findAdminMembers(search, size, offset);
        Criteria criteria = new Criteria(page, size, total, 5);

        MemberCriteriaDTO dto = new MemberCriteriaDTO();
        dto.setMembers(members);
        dto.setTotal(total);
        dto.setCriteria(criteria);
        dto.setSearch(search);

        return dto;
    }

//관리자 회원 상세
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberDTO getMemberDetail(Long memberId) {
        if (memberId == null) {
            throw new MemberNotFoundException();
        }
        MemberDTO dto = memberDAO.findAdminMemberDetail(memberId);

        if (dto == null) {
            throw new MemberNotFoundException();
        }
        return dto;
    }

    @Override
    public MemberAdminStatics getStatics() {
        MemberAdminStatics statics = new MemberAdminStatics();
        statics.setMonthlyJoins(memberDAO.findMonthlyJoin());
        statics.setTodayJoin(memberDAO.selectCountTodayJoin());
        statics.setTotalCrewCount(crewDAO.selectTotalCrewCount());
        statics.setTotalMemberCount(memberDAO.selectTotalMemberCount());
        statics.setPopularCountries(countryDAO.selectPopularCountries());
        return statics;
    }

    @Override
    public void joinAdmin(MemberDTO memberDTO) {
        memberDTO.setMemberPassword(passwordEncoder.encode(memberDTO.getMemberPassword()));

        memberDTO.setMemberName(memberDTO.getMemberName());
        memberDTO.setMemberRole(memberDTO.getMemberRole());
        memberDTO.setMemberEmail(memberDTO.getMemberEmail());
        memberDTO.setMemberPassword(memberDTO.getMemberPassword());

        memberDAO.insertAdmin(memberDTO);
    }

    @Override
    public MemberDTO getProfileMember(Long memberId) {
        MemberDTO memberDTO = memberDAO.findMemberById(memberId);
        if(memberDTO.getFilePath() != null) {
            memberDTO.setFilePath(s3Service.getPreSignedUrl(memberDTO.getFilePath(), Duration.ofMinutes(10)));
        }else{memberDTO.setFilePath("https://image.ohousecdn.com/i/bucketplace-v2-development/uploads/default_images/avatar.png?w=144&h=144&c=c");}
        memberDTO.setDiaryCount(diaryDAO.countAllByMemberId(memberId));
        log.info("profile"+memberDTO.toString());
        return memberDTO;
    }

    public Optional<MemberProfileDTO> getMember(Long memberId) {
        return Optional.empty();
    }

//  별점 등록 시 케미점수 및 상태 갱신
    @Transactional
    public void submitReview(Long sellerId, Long paymentStatusId, int rating) {
        // 판매자 케미 점수 갱신
        memberDAO.updateChemistryScore(sellerId, rating);

        // 주문 상태 reviewed 로 변경
        paymentStatusMapper.updatePaymentStatus(paymentStatusId, PaymentPhase.REVIEWED);
    }

    // 나의 판매내역 목록 조회
    @Override
    public MySaleListCriteriaDTO getSaleListByMemberId(Long memberId, Criteria criteria, Search search) {

        List<MySaleListDTO> list = memberDAO.selectSaleList(memberId, criteria, search);

        int total = memberDAO.selectSaleTotalCount(memberId, search);
        criteria.setTotal(total);

        list.forEach(dto -> {
            try {
                if (dto.getFilePath() != null && !dto.getFilePath().isBlank()) {
                    log.info("Before S3 convert filePath={}", dto.getFilePath());
                    String preSignedUrl = s3Service.getPreSignedUrl(dto.getFilePath(), Duration.ofMinutes(5));
                    log.info("After S3 convert preSignedUrl={}", preSignedUrl);
                    dto.setFilePath(preSignedUrl);
                }
            } catch (Exception e) {
                log.warn("S3 URL 변환 실패: {}", e.getMessage());
            }
        });

        MySaleListCriteriaDTO result = new MySaleListCriteriaDTO();
        result.setMySaleListDTOs(list);
        result.setCriteria(criteria);
        result.setSearch(search);

        log.info("result.getSaleListDTOs() = {}", result.getMySaleListDTOs());
        return result;
    }

    @Override
    public void updateSaleStatus(Long memberId, Long paymentStatusId, PaymentPhase paymentPhase) {
        log.info("판매 상태 변경 요청: memberId={}, paymentStatusId={}, paymentPhase={}",
                memberId, paymentStatusId, paymentPhase);

        paymentStatusMapper.updatePaymentStatus(paymentStatusId, paymentPhase);

        log.info(" 판매 상태가 {} 로 변경되었습니다.", paymentPhase);
    }
    
    // 나의 판매내역 상세 조회
    @Override
    public MySaleDetailDTO getSellerOrderDetails(Long sellerId, Long paymentStatusId) {
        // DB에서 판매 상세 데이터 조회
        MySaleDetailDTO detail = memberDAO.selectSellerOrderDetails(sellerId, paymentStatusId);

        if (detail == null) {
            log.warn("판매 상세 데이터가 없습니다. sellerId={}, paymentStatusId={}", sellerId, paymentStatusId);
            return null;
        }

        // 이미지 경로가 존재하면 S3 프리사인드 URL로 변환
        try {
            if (detail.getMainImage() != null && !detail.getMainImage().isBlank()) {
                String preSignedUrl = s3Service.getPreSignedUrl(detail.getMainImage(), Duration.ofMinutes(5));
                detail.setMainImage(preSignedUrl);
                log.info("판매 상세 이미지 S3 프리사인드 URL 변환 성공: {}", preSignedUrl);
            } else {
                log.info("판매 상세 이미지 없음 (mainImage 필드 null 또는 공백)");
            }
        } catch (Exception e) {
            log.error("S3 프리사인드 URL 변환 실패 - paymentStatusId={}, error={}", paymentStatusId, e.getMessage());
        }

        return detail;
    }

    @Override
    public ModifyDTO getMemberInfo(CustomUserDetails customUserDetails) {
        Long memberId = customUserDetails.getId();
        ModifyDTO dto = memberDAO.selectMemberInfo(memberId);

        String imageUrl = null;

        // S3 이미지 존재 여부
        if (dto.getFilePath() != null && dto.getFileName() != null) {
            imageUrl = dto.getFilePath() + dto.getFileName();
        }
        // 소셜 이미지 존재 여부
        else if (dto.getProfileImageUrl() != null && !dto.getProfileImageUrl().isEmpty()) {
            imageUrl = dto.getProfileImageUrl();
        }
        // 기본 이미지
        else {
            imageUrl = "https://image.ohousecdn.com/i/bucketplace-v2-development/uploads/default_images/avatar.png?w=144&h=144&c=c";
        }

        dto.setProfileImageUrl(imageUrl);
        return dto;
    }


    @Override
    public void deleteCache(String keyName){
        String name = keyName+"*";
        Set<String> keys = redisTemplate.keys(name);
        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }


}
