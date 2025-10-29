package com.example.crewstation.banner;

import com.example.crewstation.dto.banner.BannerDTO;
import com.example.crewstation.service.banner.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class ServiceTests {
    @Autowired
    private BannerService bannerService;

    @Test
    public void getBannerTest(){
        List<BannerDTO> banners = bannerService.getBanners(4);
        log.info("banners:{}",banners);
    }

    @Test
    public void insertBannerTest(){
        BannerDTO bannerDTO = new BannerDTO();
        bannerDTO.setBannerId(8L);
        bannerDTO.setFileId(8L);
        bannerDTO.setBannerOrder(8);
        bannerDTO.setFilePath("/banner.jpg");
        bannerDTO.setFileName("banner.jpg");

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",                          // 필드 이름
                "test.png",                      // 파일명
                "image/png",                     // MIME 타입
                "dummy image content".getBytes() // 파일 내용
        );
        List<MultipartFile> multipartFiles = Arrays.asList(multipartFile);
        bannerService.insertBannerFile(bannerDTO,multipartFiles);
    }
}
