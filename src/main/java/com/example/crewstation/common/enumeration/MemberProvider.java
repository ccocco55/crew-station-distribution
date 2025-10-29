package com.example.crewstation.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum MemberProvider {
    KAKAO("kakao"), GOOGLE("google"), NAVER("naver"), CREWSTATION("crewstation"), ;

    private final String value;
    private static final Map<String, MemberProvider> STATUS_MAP =
            Arrays.stream(MemberProvider.values()).collect(Collectors.toMap(MemberProvider::getValue, Function.identity()));
    MemberProvider(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return this.value;
    }
    @JsonCreator
    public static MemberProvider getStatusFromValue(String value) {
        return Optional.ofNullable(STATUS_MAP.get(value)).orElseThrow(IllegalArgumentException::new);
    }

}
