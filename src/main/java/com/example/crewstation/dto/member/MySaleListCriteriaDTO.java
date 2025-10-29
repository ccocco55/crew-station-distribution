package com.example.crewstation.dto.member;


import com.example.crewstation.dto.purchase.PurchaseListDTO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ToString
public class MySaleListCriteriaDTO {
    private List<MySaleListDTO> mySaleListDTOs ;
    private Criteria criteria;
    private Search search;
}
