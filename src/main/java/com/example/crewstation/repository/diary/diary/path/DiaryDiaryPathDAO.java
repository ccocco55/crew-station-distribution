package com.example.crewstation.repository.diary.diary.path;

import com.example.crewstation.domain.diary.diary.path.DiaryDiaryPathVO;
import com.example.crewstation.mapper.diary.diary.path.DiaryDiaryPathMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DiaryDiaryPathDAO {
    private final DiaryDiaryPathMapper diaryDiaryPathMapper;

    // 경로와 연결
    public void save(DiaryDiaryPathVO diaryDiaryPathVO) {
        diaryDiaryPathMapper.insert(diaryDiaryPathVO);
    }
}
