package com.example.crewstation.payment;

import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.service.payment.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class ServiceTest {
    @Autowired
    private PaymentService paymentService;

    @Test
    @Transactional
    public void testInsert() {
//        PaymentStatusDTO paymentStatusDTO = new PaymentStatusDTO();
//        paymentStatusDTO.setPurchaseId(1L);
//        paymentStatusDTO.setMemberId(1L);
//
//        paymentService.requestPayment(paymentStatusDTO);
    }
}
