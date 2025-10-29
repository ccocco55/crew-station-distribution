package com.example.crewstation.dto.accompany;

import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AccompanyCriteriaDTO {
    private List<AccompanyDTO> accompanyDTOs;
    private Criteria criteria;
    private Search search;
    private int totalCount;
}
