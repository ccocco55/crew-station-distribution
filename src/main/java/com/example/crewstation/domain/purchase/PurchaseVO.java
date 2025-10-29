package com.example.crewstation.domain.purchase;


import com.example.crewstation.audit.Period;
import com.example.crewstation.common.enumeration.DeliveryMethod;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "postId",callSuper = false)
public class PurchaseVO extends Period {
    private Long postId;
    private int purchaseProductPrice;
    private int purchaseLimitTime;
    private int purchaseProductCount;
    private String purchaseCountry;
    private DeliveryMethod deliveryMethod;
}
