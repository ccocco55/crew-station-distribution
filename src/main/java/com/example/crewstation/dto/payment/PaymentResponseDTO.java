package com.example.crewstation.dto.payment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
public class PaymentResponseDTO {
    private String message;
    private boolean guest;
    private int count;

}
