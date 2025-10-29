package com.example.crewstation.service.diary;

import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.dto.accompany.AccompanyPathDTO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.repository.section.SectionDAO;
import com.example.crewstation.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryTransactionService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final S3Service s3Service;
    private final DiaryDTO diaryDTO;
    private final SectionDAO sectionDAO;
    private final DiaryDAO diaryDAO;

    @Transactional(rollbackFor = Exception.class)
    public List<DiaryDTO> selectDiaryList (int limit) {
        List<DiaryDTO> diaries = diaryDAO.selectDiaryList(limit);
        diaries.forEach(diary -> {
            String filePath = diary.getDiaryFilePath();
            String presignedUrl = s3Service.getPreSignedUrl(filePath, Duration.ofMinutes(5));
            diary.setFileCount(sectionDAO.findSectionFileCount(diary.getPostId()));
            if(diary.getMemberFilePath() != null){
                diary.setMemberFilePath(s3Service.getPreSignedUrl(diary.getMemberFilePath(), Duration.ofMinutes(5)));
            }
            log.info("Diary ID={}, 원본 DiaryfilePath={}, 발급된 presignedUrl={}",
                    diary, filePath, presignedUrl);
            diary.setDiaryFilePath(presignedUrl);
        });
        redisTemplate.opsForValue().set("diaries", diaries, Duration.ofMinutes(5));
        return diaries;
    }
}
