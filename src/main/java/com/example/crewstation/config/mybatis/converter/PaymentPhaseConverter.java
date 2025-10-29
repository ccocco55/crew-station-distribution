package com.example.crewstation.config.mybatis.converter;


import com.example.crewstation.common.enumeration.PaymentPhase;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentPhaseConverter implements Converter<String, PaymentPhase> {

    @Override
    public PaymentPhase convert(String source) {
        return PaymentPhase.getPaymentStatusFromValue(source);
    }
}
