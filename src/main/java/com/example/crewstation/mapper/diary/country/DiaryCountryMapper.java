package com.example.crewstation.mapper.diary.country;

import com.example.crewstation.domain.diary.country.DiaryCountryVO;
import com.example.crewstation.dto.country.CountryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryCountryMapper {
// 나라와 연결
    public void insert(DiaryCountryVO diaryCountryVO);

    public List<CountryDTO> selectCountryByPostId(Long postId);

    //   나라 삭제
    public void delete(Long countryId);
}

