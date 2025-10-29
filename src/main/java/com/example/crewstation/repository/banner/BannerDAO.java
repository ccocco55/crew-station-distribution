package com.example.crewstation.repository.banner;

import com.example.crewstation.dto.banner.BannerDTO;
import com.example.crewstation.dto.file.FileDTO;
import com.example.crewstation.mapper.banner.BannerMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BannerDAO {
    private final BannerMapper bannerMapper;

//    배너 리스트 조회하기
    public List<BannerDTO> getBanners(@Param("limit") int limit) {
        return bannerMapper.getBanners(limit);
    }

//    배너 추가
    public void insertBanner(BannerDTO bannerDTO) {
        bannerMapper.insertBanner(bannerDTO);
    }

//    배너 파일 추가
    public void insertBannerFile(@Param("bannerId") Long bannerId,
                                 @Param("fileId")   Long fileId) {
        bannerMapper.insertBannerFile(bannerId, fileId);
    }

//    배너 수정
    public void updateBannerFile(BannerDTO bannerDTO) {
        bannerMapper.updateBannerFile(bannerDTO);
    }

//    배너 삭제
    public void deleteBanner(@Param("bannerId") Long bannerId) {
        bannerMapper.deleteBanner(bannerId);

    }
//  배너 파일 삭제
    public void deleteBannerFileLink(@Param("bannerId") Long bannerId,
                              @Param("fileId")   Long fileId){
        bannerMapper.deleteBannerFileLink(bannerId, fileId);
    }

//    배너 링크 전체 삭제
    public void deleteAllBannerFiles(Long bannerId) {
        bannerMapper.deleteAllBannerFiles(bannerId);
    }

//    배너 연결 파일 목록 조회
    public List<FileDTO> findFilesByBannerId(Long bannerId) {
        return bannerMapper.findFilesByBannerId(bannerId);
    }


}
