package com.example.crewstation.dto.crew;

import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CrewCriteriaDTO {

    private List<CrewDTO> crewDTOs;
    private Criteria criteria;
    private int totalCount;
    private Search search;

}
