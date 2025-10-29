package com.example.crewstation.dto.guest;

import com.example.crewstation.common.enumeration.DeliveryMethod;
import com.example.crewstation.common.enumeration.PaymentPhase;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class GuestOrderDetailDTO {
    // 주문(게스트) 정보
    private Long guestId;
    private String guestName;
    private String guestPhone;
    private String addressZipCode;
    private String guestAddress;
    private String guestAddressDetail;
    private String guestOrderNumber;

    // 결제
    private Long paymentStatusId;
    private PaymentPhase paymentStatus;

    // 구매(상품)
    private Long buyerMemberId;
    private Long postId;
    private Long purchaseId;
    private String postTitle;
    private int purchaseProductPrice;
    private String purchaseCountry;
    private DeliveryMethod purchaseDeliveryMethod;
    private String mainImage;

    // 판매자
    private Long sellerId;
    private String sellerName;
    private String sellerPhone;
    private String createdDatetime;
    private String updatedDatetime;
}
