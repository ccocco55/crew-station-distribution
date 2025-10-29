package com.example.crewstation.crew;

import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.service.accompany.AccompanyService;
import com.example.crewstation.service.crew.CrewService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class ServiceTests {
    @Autowired
    private CrewService crewService;

    @Test
    public void getCrewsTest(){
        CrewDTO crewDTO = new CrewDTO();
        List<CrewDTO> crewDTOs = crewService.getCrews();

        log.info("CrewDTO={}", crewDTOs);
    }
}
