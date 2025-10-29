package com.example.crewstation.config.mybatis.converter;


import com.example.crewstation.common.enumeration.MemberProvider;
import com.example.crewstation.common.enumeration.Secret;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SecretProviderConverter implements Converter<String, Secret> {

    @Override
    public Secret convert(String source) {
        return Secret.getSecretFromValue(source);
    }
}
