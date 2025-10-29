package com.example.crewstation.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Secret {
        PUBLIC("public"), PRIVATE("private");

        private final String value;
        private static final Map<String, com.example.crewstation.common.enumeration.Secret> SECRET_MAP =
                Arrays.stream(com.example.crewstation.common.enumeration.Secret.values()).collect(Collectors.toMap(com.example.crewstation.common.enumeration.Secret::getValue, Function.identity()));
        Secret(String value) {
            this.value = value;
        }
        @JsonValue
        public String getValue() {
            return this.value;
        }
        @JsonCreator
        public static com.example.crewstation.common.enumeration.Secret getSecretFromValue(String value) {
            return Optional.ofNullable(SECRET_MAP.get(value)).orElseThrow(IllegalArgumentException::new);
        }
}
