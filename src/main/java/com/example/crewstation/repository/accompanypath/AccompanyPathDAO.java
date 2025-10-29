package com.example.crewstation.repository.accompanypath;

import com.example.crewstation.dto.accompany.AccompanyPathDTO;
import com.example.crewstation.mapper.accompany.AccompanyPathMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccompanyPathDAO {

    private final AccompanyPathMapper accompanyPathMapper;

    public List<AccompanyPathDTO> findCountryPath() {
        return accompanyPathMapper.findCountryPath();
    }

}
