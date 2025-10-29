package com.example.crewstation.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum PaymentPhase {
    REQUEST("request"),PENDING("pending"),REFUND("refund"),SUCCESS("success"), RECEIVED("received"), REVIEWED("reviewed");

    private final String value;
    private static final Map<String, PaymentPhase> PAYMENT_STATUS_MAP_MAP =
            Arrays.stream(PaymentPhase.values()).collect(Collectors.toMap(PaymentPhase::getValue, Function.identity()));
    PaymentPhase(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return this.value;
    }
    @JsonCreator
    public static PaymentPhase getPaymentStatusFromValue(String value) {
        return Optional.ofNullable(PAYMENT_STATUS_MAP_MAP.get(value)).orElseThrow(IllegalArgumentException::new);
    }
}
