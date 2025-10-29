package com.example.crewstation.domain.payment.status;

import com.example.crewstation.audit.Period;
import com.example.crewstation.common.enumeration.PaymentPhase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id",callSuper = false)
public class PaymentStatusVO extends Period {
    private Long id;
    private Long paymentId;
    private Long purchaseId;
    private PaymentPhase paymentPhase;
}
