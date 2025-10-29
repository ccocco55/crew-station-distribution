package com.example.crewstation.dto.member;

import com.example.crewstation.common.enumeration.DeliveryMethod;
import com.example.crewstation.common.enumeration.PaymentPhase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MySaleDetailDTO {
    // 구매자 정보
    private Long buyerMemberId;
    private String memberName;
    private String memberPhone;
    private String addressZipCode;
    private String Address;
    private String AddressDetail;

    // 결제
    private Long paymentStatusId;
    private PaymentPhase paymentPhase;

    // 구매(상품)
    private Long postId;
    private String postTitle;
    private int purchaseProductPrice;
    private String mainImage;

    // 판매자
    private Long sellerId;
    private String createdDatetime;
    private String updatedDatetime;
}
