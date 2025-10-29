package com.example.crewstation.dto.payment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
public class AdminPaymentSearchDTO {

    private List<String> categories; // ps.payment_phase 값들
    private String keyword;          // buyer name/email 검색
    private int page = 1;        // 1-based
    private int size = 20;
}
