package com.example.crewstation.mapper.diary.diary.path;

import com.example.crewstation.domain.diary.diary.path.DiaryDiaryPathVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiaryDiaryPathMapper {
// 경로와 연결
    public void insert(DiaryDiaryPathVO diaryDiaryPathVO);
}
