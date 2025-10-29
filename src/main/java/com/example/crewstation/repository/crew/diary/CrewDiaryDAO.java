package com.example.crewstation.repository.crew.diary;

import com.example.crewstation.domain.crew.CrewDiaryVO;
import com.example.crewstation.mapper.crew.diary.CrewDiaryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CrewDiaryDAO {
    private final CrewDiaryMapper crewDiaryMapper;


    public void save(CrewDiaryVO crewDiaryVO){
        crewDiaryMapper.insert(crewDiaryVO);
    }
}
