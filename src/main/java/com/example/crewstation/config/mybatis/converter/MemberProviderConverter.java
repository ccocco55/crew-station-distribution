package com.example.crewstation.config.mybatis.converter;


import com.example.crewstation.common.enumeration.MemberProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberProviderConverter implements Converter<String, MemberProvider> {

    @Override
    public MemberProvider convert(String source) {
        return MemberProvider.getStatusFromValue(source);
    }
}
