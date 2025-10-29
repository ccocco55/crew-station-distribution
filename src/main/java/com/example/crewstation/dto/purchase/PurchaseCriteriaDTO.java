package com.example.crewstation.dto.purchase;


import com.example.crewstation.common.enumeration.DeliveryMethod;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ToString
public class PurchaseCriteriaDTO {
    private List<PurchaseDTO> purchaseDTOs;
    private Criteria criteria;
    private Search search;
}
