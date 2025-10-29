package com.example.crewstation.diary;

import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.service.diary.DiaryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class MainDiaryServiceTests {
    @Autowired
    private DiaryService diaryService;

    @Test
    public void selectDiaryListTest(){
        List<DiaryDTO> diaryList = diaryService.selectDiaryList(4);
        log.info("diaryList: {}", diaryList);
    }
}
