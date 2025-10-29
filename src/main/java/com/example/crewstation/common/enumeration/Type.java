package com.example.crewstation.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Type {

    MAIN("main"),SUB("sub") ;

    private final String value;
    private static final Map<String, Type> TYPE_MAP =
            Arrays.stream(Type.values()).collect(Collectors.toMap(Type::getValue, Function.identity()));
    Type(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return this.value;
    }
    @JsonCreator
    public static Type getTypeFromValue(String value) {
        return Optional.ofNullable(TYPE_MAP.get(value)).orElseThrow(IllegalArgumentException::new);
    }
}
