package com.example.crewstation.diary;

import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.mapper.diary.DiaryMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest
@Slf4j
public class DiaryMapperTests {

    @Autowired
    private DiaryMapper diaryMapper;


    @Test
    public void selectDiaryListTest() {
        DiaryDTO diaryDTO = new DiaryDTO();
        List<DiaryDTO> dtos =  diaryMapper.selectDiaryList(4);
        log.info("diaryDTO={}", dtos);
        }
    }

