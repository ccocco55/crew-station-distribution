package com.example.crewstation.dto.payment;

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
public class PaymentDTO {
    private Long id;
    private Long memberId;
    private Long purchaseId;
    private String receiptId;
    private Long paymentStatusId;
    private Integer paymentAmount;
    private String createdDatetime;
    private String updatedDatetime;
}
