package com.example.crewstation.mapper.country;

import com.example.crewstation.dto.country.CountryDTO;
import com.example.crewstation.dto.country.CountryStatics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CountryMapper {
//   나라 가져오기
    public List<CountryDTO> selectAll();

//    인기 여행지 통계
    public List<CountryStatics> selectPopularCountries();
}
