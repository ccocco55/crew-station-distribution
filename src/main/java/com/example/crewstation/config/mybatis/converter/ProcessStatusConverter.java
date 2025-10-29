package com.example.crewstation.config.mybatis.converter;


import com.example.crewstation.common.enumeration.ProcessStatus;
import com.example.crewstation.common.enumeration.Status;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProcessStatusConverter implements Converter<String, ProcessStatus> {

    @Override
    public ProcessStatus convert(String source) {
        return ProcessStatus.getProcessStatusFromValue(source);
    }
}
