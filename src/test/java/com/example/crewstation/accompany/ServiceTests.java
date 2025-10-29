package com.example.crewstation.accompany;

import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.service.accompany.AccompanyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class ServiceTests {
    @Autowired
    private AccompanyService accompanyService;

    @Test
    public void getAccompaniesTest() {
        List<AccompanyDTO> accompanies = accompanyService.getAccompanies(4);

        log.info("accompanies: {}", accompanies);
    }
}
