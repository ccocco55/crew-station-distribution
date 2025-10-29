package com.example.crewstation.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CrewRole {
    LEADER("leader"), PARTNER("partner");

    private final String value;
    private static final Map<String, CrewRole> CREW_ROLE_MAP =
            Arrays.stream(CrewRole.values()).collect(Collectors.toMap(CrewRole::getValue, Function.identity()));
    CrewRole(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return this.value;
    }
    @JsonCreator
    public static CrewRole getCrewRoleFromValue(String value) {
        return Optional.ofNullable(CREW_ROLE_MAP.get(value)).orElseThrow(IllegalArgumentException::new);
    }
}
