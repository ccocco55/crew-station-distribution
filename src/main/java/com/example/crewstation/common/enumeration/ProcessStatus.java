package com.example.crewstation.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

//create type process_status as enum ('pending','reject','resolved');
public enum ProcessStatus {
    PENDING("pending"),REJECT("reject"),RESOLVED("resolved");

    private final String value;
    private static final Map<String, ProcessStatus> PROCESS_STATUS_MAP_MAP =
            Arrays.stream(ProcessStatus.values()).collect(Collectors.toMap(ProcessStatus::getValue, Function.identity()));
    ProcessStatus(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return this.value;
    }
    @JsonCreator
    public static ProcessStatus getProcessStatusFromValue(String value) {
        return Optional.ofNullable(PROCESS_STATUS_MAP_MAP.get(value)).orElseThrow(IllegalArgumentException::new);
    }

}
