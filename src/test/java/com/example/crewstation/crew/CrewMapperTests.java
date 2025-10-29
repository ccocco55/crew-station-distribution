package com.example.crewstation.crew;

import com.example.crewstation.domain.crew.CrewVO;
import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.mapper.crew.CrewMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


// branch temp
@SpringBootTest
@Slf4j
public class CrewMapperTests {

    @Autowired
    private CrewMapper crewMapper;

    @Test
    public void testGetCrews() {
        CrewDTO dto = new CrewDTO();
        List<CrewDTO> crewDTOS  = crewMapper.getCrews();

        log.info("CrewDTO={}", crewDTOS);







    }

}
