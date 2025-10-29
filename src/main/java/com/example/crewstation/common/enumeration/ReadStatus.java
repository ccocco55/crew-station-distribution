package com.example.crewstation.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ReadStatus {
    READ("active"), UNREAD("inactive");

    private final String value;
    private static final Map<String, ReadStatus> READ_STATUS_MAP =
            Arrays.stream(ReadStatus.values()).collect(Collectors.toMap(ReadStatus::getValue, Function.identity()));
    ReadStatus(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return this.value;
    }
    @JsonCreator
    public static ReadStatus getReadStatusFromValue(String value) {
        return Optional.ofNullable(READ_STATUS_MAP.get(value)).orElseThrow(IllegalArgumentException::new);
    }
}
