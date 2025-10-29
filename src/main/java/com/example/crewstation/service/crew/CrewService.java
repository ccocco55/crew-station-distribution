package com.example.crewstation.service.crew;


import com.example.crewstation.dto.crew.CrewCriteriaDTO;
import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.util.Search;

import java.util.List;

public interface CrewService {
//    크루 목록
    public List<CrewDTO> getCrews();

//    크루 검색 목록
    public CrewCriteriaDTO getSearchCrews(Search search);
}
