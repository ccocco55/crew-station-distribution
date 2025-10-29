package com.example.crewstation.dto.gift;

import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@ToString
@Component
public class GiftCriteriaDTO {
    private List<GiftDTO> giftDTOs;
    private Criteria criteria;
    private Search search;
    private int totalCount;

}
