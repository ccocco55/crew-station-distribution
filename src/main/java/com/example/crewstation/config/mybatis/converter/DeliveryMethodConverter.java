package com.example.crewstation.config.mybatis.converter;


import com.example.crewstation.common.enumeration.DeliveryMethod;
import com.example.crewstation.common.enumeration.Status;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMethodConverter implements Converter<String, DeliveryMethod> {

    @Override
    public DeliveryMethod convert(String source) {
        return DeliveryMethod.getDeliveryMethodFromValue(source);
    }
}
