package com.example.crewstation.domain.payment;

import com.example.crewstation.audit.Period;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id",callSuper = false)
public class PaymentVO extends Period {
    private Long id;
    private Long memberId;
}
