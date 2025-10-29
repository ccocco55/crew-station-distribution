package com.example.crewstation.config.mybatis.converter;


import com.example.crewstation.common.enumeration.Status;
import com.example.crewstation.common.enumeration.Type;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TypeConverter implements Converter<String, Type> {

    @Override
    public Type convert(String source) {
        return Type.getTypeFromValue(source);
    }
}
