package com.example.crewstation.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum MemberRole {
    MEMBER("member"), ADMIN("admin");

    private final String value;
    private static final Map<String, MemberRole> STATUS_MAP =
            Arrays.stream(MemberRole.values()).collect(Collectors.toMap(MemberRole::getValue, Function.identity()));
    MemberRole(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return this.value;
    }
    @JsonCreator
    public static MemberRole getStatusFromValue(String value) {
        return Optional.ofNullable(STATUS_MAP.get(value)).orElseThrow(IllegalArgumentException::new);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }

}
