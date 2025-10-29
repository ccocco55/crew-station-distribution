package com.example.crewstation.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Status {
    ACTIVE("active"), INACTIVE("inactive");

    private final String value;
    private static final Map<String, Status> STATUS_MAP =
            Arrays.stream(Status.values()).collect(Collectors.toMap(Status::getValue, Function.identity()));
    Status(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return this.value;
    }
    @JsonCreator
    public static Status getStatusFromValue(String value) {
        return Optional.ofNullable(STATUS_MAP.get(value)).orElseThrow(IllegalArgumentException::new);
    }

}
