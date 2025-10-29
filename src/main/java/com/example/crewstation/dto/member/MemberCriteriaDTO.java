package com.example.crewstation.dto.member;

import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MemberCriteriaDTO {
    private List<MemberDTO> members;
    private int total;
    private Criteria criteria;
    private Search search;
}
