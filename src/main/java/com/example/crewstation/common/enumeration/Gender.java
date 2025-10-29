package com.example.crewstation.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Gender {
    MALE("male"), FEMALE("female");

    private final String value;
    private static final Map<String, Gender> STATUS_MAP =
            Arrays.stream(Gender.values()).collect(Collectors.toMap(Gender::getValue, Function.identity()));
    Gender(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return this.value;
    }
    @JsonCreator
    public static Gender getStatusFromValue(String value) {
        return Optional.ofNullable(STATUS_MAP.get(value)).orElseThrow(IllegalArgumentException::new);
    }

}
