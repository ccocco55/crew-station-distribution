package com.example.crewstation.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum DeliveryMethod {
//    create type delivery_method as enum ('direct','parcel');
    DIRECT("direct"), PARCEL("parcel");

    private final String value;
    private static final Map<String, DeliveryMethod> DELIVERYMETHOD_MAP =
            Arrays.stream(DeliveryMethod.values()).collect(Collectors.toMap(DeliveryMethod::getValue, Function.identity()));
    DeliveryMethod(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return this.value;
    }
    @JsonCreator
    public static DeliveryMethod getDeliveryMethodFromValue(String value) {
        return Optional.ofNullable(DELIVERYMETHOD_MAP.get(value)).orElseThrow(IllegalArgumentException::new);
    }
}
