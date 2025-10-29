package com.example.crewstation.config.mybatis.converter;


import com.example.crewstation.common.enumeration.Gender;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GenderConverter implements Converter<String, Gender> {

    @Override
    public Gender convert(String source) {
        return Gender.getStatusFromValue(source);
    }
}
