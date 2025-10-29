package com.example.crewstation.service.crew;

import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.repository.crew.CrewDAO;
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
public class CrewTransactionService {
    private final CrewDAO crewDAO;
    private final RedisTemplate<String, Object> redisTemplate;
    private final S3Service s3Service;

    @Transactional(rollbackFor = Exception.class)
    public List<CrewDTO> getCrews() {
        List<CrewDTO> crews = crewDAO.getCrews();
        crews.forEach(crew -> {
            String originalPath = crew.getFilePath();
            String presignedUrl = s3Service.getPreSignedUrl(originalPath, Duration.ofMinutes(5));

            log.info("Crew ID={}, 원본 filePath={}, 발급된 presignedUrl={}",
                    crew.getId(), originalPath, presignedUrl);
            crew.setFilePath(s3Service.getPreSignedUrl(crew.getFilePath(),
                    Duration.ofMinutes(15)));

        });

        redisTemplate.opsForValue().set("crews", crews, Duration.ofMinutes(5));

        return crews;
    }
}
