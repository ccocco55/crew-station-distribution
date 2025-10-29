package com.example.crewstation.mapper.diary.file;

import com.example.crewstation.dto.diary.file.DiaryFileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiaryFileMapper {
//    다이러리 파일 조회
    List<DiaryFileDTO> getDiaryFilesByDiaryId(@Param("diaryId") Long diaryId);
}
