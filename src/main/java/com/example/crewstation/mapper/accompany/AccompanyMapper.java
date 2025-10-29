package com.example.crewstation.mapper.accompany;

import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccompanyMapper {
//    동행 리스트 목록 조회
    public List<AccompanyDTO> getAccompanies(@Param("limit") int limit);

//    동행 목록 개수
    public int countAccompanies(@Param("search") Search search);

//    동행 검색 목록
    public List<AccompanyDTO> findAccompanies(@Param("search") Search search ,
                                              @Param("criteria") Criteria criteria
                                              );


}
