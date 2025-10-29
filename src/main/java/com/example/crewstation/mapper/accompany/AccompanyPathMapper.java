package com.example.crewstation.mapper.accompany;

import com.example.crewstation.dto.accompany.AccompanyPathDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccompanyPathMapper {
    public List<AccompanyPathDTO> findCountryPath();
}
