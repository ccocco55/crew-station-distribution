package com.example.crewstation.config.mybatis.converter;


import com.example.crewstation.common.enumeration.ReadStatus;
import com.example.crewstation.common.enumeration.Status;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReadStatusConverter implements Converter<String, ReadStatus> {

    @Override
    public ReadStatus convert(String source) {
        return ReadStatus.getReadStatusFromValue(source);
    }
}
