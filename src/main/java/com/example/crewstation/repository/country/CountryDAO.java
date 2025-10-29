package com.example.crewstation.repository.country;

import com.example.crewstation.dto.country.CountryDTO;
import com.example.crewstation.dto.country.CountryStatics;
import com.example.crewstation.mapper.country.CountryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CountryDAO {
    private final CountryMapper countryMapper;
    //   나라 가져오기
    public List<CountryDTO> findAll(){
        return countryMapper.selectAll();
    }

//    인기 여행지 통계
    public List<CountryStatics> selectPopularCountries() {
        return countryMapper.selectPopularCountries();
    }
}
