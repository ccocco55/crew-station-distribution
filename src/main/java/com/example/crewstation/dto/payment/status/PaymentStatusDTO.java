package com.example.crewstation.dto.payment.status;

import com.example.crewstation.common.enumeration.PaymentPhase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
public class PaymentStatusDTO {
    private Long id;
    private Long paymentId;
    private Long purchaseId;
    private Long memberId;
    private boolean guest;
    private String memberPhone;
    private String addressZipCode;
    private String addressDetail;
    private String address;
    private String guestOrderNumber;
    private String guestPassword;
    private PaymentPhase paymentPhase;
    private String createdDatetime;
    private String updatedDatetime;
}
