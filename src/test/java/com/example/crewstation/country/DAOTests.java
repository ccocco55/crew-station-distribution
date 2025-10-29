package com.example.crewstation.country;


import com.example.crewstation.dto.country.CountryDTO;
import com.example.crewstation.mapper.country.CountryMapper;
import com.example.crewstation.repository.country.CountryDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class DAOTests {
    @Autowired
    private CountryDAO countryDAO;

    @Test
    public void testfindAll(){
        List<CountryDTO> all = countryDAO.findAll();

        all.stream().map(CountryDTO::toString).forEach(log::info);

    }
}
