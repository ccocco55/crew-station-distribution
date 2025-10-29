package com.example.crewstation.service.banner;

import com.example.crewstation.dto.banner.BannerDTO;
import com.example.crewstation.repository.banner.BannerDAO;
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
public class BannerTransactionService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final S3Service s3Service;
    private final BannerDAO bannerDAO;

    @Transactional(rollbackFor = Exception.class)
    public List<BannerDTO> getBanners(int limit) {
        log.info("getBanners={}", limit);
        List<BannerDTO> banners = bannerDAO.getBanners(limit);
        banners.forEach(banner -> {
            String filePath = banner.getFilePath();
            String presignedUrl = s3Service.getPreSignedUrl(filePath, Duration.ofMinutes(5));

            log.info("Banner ID={}, 원본 filePath={}, 발급된 presignedUrl={}",
                    banner, filePath, presignedUrl);
            banner.setFilePath(s3Service.getPreSignedUrl(banner.getFilePath(),
                    Duration.ofMinutes(5)));
        });
        return banners;
    }
}
