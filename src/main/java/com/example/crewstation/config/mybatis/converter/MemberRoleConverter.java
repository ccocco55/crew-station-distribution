package com.example.crewstation.config.mybatis.converter;


import com.example.crewstation.common.enumeration.MemberRole;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberRoleConverter implements Converter<String, MemberRole> {

    @Override
    public MemberRole convert(String source) {
        return MemberRole.getStatusFromValue(source);
    }
}
