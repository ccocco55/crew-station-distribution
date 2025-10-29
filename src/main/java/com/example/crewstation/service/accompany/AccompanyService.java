package com.example.crewstation.service.accompany;


import com.example.crewstation.dto.accompany.AccompanyCriteriaDTO;
import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccompanyService {
    List<AccompanyDTO> getAccompanies(int limit);

    AccompanyCriteriaDTO getSearchAccompanies(Search search);
}
