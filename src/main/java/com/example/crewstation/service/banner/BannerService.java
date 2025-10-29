package com.example.crewstation.service.banner;

import com.example.crewstation.dto.banner.BannerDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService {

//    배너 리스트 조회
    List<BannerDTO> getBanners(@Param("limit") int limit);

//    배너 추가
//    배너 파일 추가
    public void insertBannerFile(BannerDTO bannerDTO , List<MultipartFile> files);

//    배너 수정
    public void updateBanner(BannerDTO bannerDTO, Long[] deleteFiles, List<MultipartFile> newfiles);

//    배너 삭제
    public void deleteBanner(Long id);

}
