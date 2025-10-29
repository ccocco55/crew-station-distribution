package com.example.crewstation.service.accompany;

import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.dto.accompany.AccompanyPathDTO;
import com.example.crewstation.repository.accompany.AccompanyDAO;
import com.example.crewstation.repository.accompanypath.AccompanyPathDAO;
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
public class AccompanyTransactionService {
    private final AccompanyDAO accompanyDAO;
    private final RedisTemplate<String, Object> redisTemplate;
    private final S3Service s3Service;
    private final AccompanyPathDAO accompanyPathDAO;

    @Transactional(rollbackFor = Exception.class)
    public List<AccompanyDTO> getAccompanies(int limit) {
        List<AccompanyDTO> accompanies = accompanyDAO.getAccompanies(limit);
        accompanies.forEach(accompany -> {
            String filePath = accompany.getFilePath();
            String presignedUrl = s3Service.getPreSignedUrl(filePath, Duration.ofMinutes(5));

            List<AccompanyPathDTO> accompanyPathDTOS = accompanyPathDAO.findCountryPath();
            accompany.setAccompanyPathDTO(accompanyPathDTOS);

            log.info("Accompany ID={}, 원본 filePath={}, 발급된 presignedUrl={}",
                    accompany, filePath, presignedUrl);
            accompany.setFilePath(s3Service.getPreSignedUrl(accompany.getFilePath(),
                    Duration.ofMinutes(5)));
        });
        redisTemplate.opsForValue().set("accompanies", accompanies, Duration.ofMinutes(5));
        return accompanies;
    }

}
