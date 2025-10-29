package com.example.crewstation.mapper.gift;

import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GiftMapper {

//    기프트 리스트 조회
    public List<GiftDTO> getGift(@Param("limit") int limit);

//    기프트 목록 개수
    public int countGifts(@Param("search") Search search);

//    기프트 검색 목록
    public List<GiftDTO> findGifts(@Param("criteria") Criteria criteria,
                                 @Param("search") Search search);
}
