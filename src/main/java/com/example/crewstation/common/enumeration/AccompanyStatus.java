package com.example.crewstation.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum AccompanyStatus {
    LONG("long"), SHORT("short");

    private final String value;
    private static final Map<String, AccompanyStatus> ACCOMPANY_STATUS_MAP =
            Arrays.stream(AccompanyStatus.values()).collect(Collectors.toMap(AccompanyStatus::getValue, Function.identity()));
    AccompanyStatus(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return this.value;
    }
    @JsonCreator
    public static AccompanyStatus getAccompanyStatusFromValue(String value) {
        return Optional.ofNullable(ACCOMPANY_STATUS_MAP.get(value)).orElseThrow(IllegalArgumentException::new);
    }
}
