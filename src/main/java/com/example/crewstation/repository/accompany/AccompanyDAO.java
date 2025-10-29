package com.example.crewstation.repository.accompany;

import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.mapper.accompany.AccompanyMapper;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccompanyDAO {
    private final AccompanyMapper accompanyMapper;

    public List<AccompanyDTO> getAccompanies(int limit) {
        return accompanyMapper.getAccompanies(limit);

    }

    public int countAccompanies(Search search) {
        return accompanyMapper.countAccompanies(search);
    }

    public List<AccompanyDTO> findAccompanies(Search search, Criteria criteria) {
        return accompanyMapper.findAccompanies(search, criteria);
    }
}
