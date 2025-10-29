package com.example.crewstation.repository.payment;


import com.example.crewstation.dto.payment.PaymentDTO;
import com.example.crewstation.mapper.payment.PaymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class PaymentDAO {
    private final PaymentMapper paymentMapper;

    public int insertPayment(PaymentDTO paymentDTO) {
        return paymentMapper.insertPayment(paymentDTO);
    }

}
