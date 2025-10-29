package com.example.crewstation.mapper.payment;

import com.example.crewstation.dto.payment.PaymentDTO;
import com.example.crewstation.util.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {
    public int insertPayment(PaymentDTO paymentDTO);
}
