package com.example.crewstation.mapper.crew.diary;

import com.example.crewstation.domain.crew.CrewDiaryVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrewDiaryMapper {
//    크루다이어리 삽입
    public void insert(CrewDiaryVO crewDiaryVO);
}
