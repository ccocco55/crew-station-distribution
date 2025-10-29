package com.example.crewstation.payment.status;

import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.mapper.payment.status.PaymentStatusMapper;
import com.example.crewstation.mapper.post.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class MapperTest {

    @Autowired
    private PaymentStatusMapper paymentStatusMapper;

    @Test
//    @Transactional
    public void testInsert() {
        PaymentStatusDTO paymentStatusDTO = new PaymentStatusDTO();
        paymentStatusDTO.setPurchaseId(1L);
        paymentStatusDTO.setMemberId(1L);
        paymentStatusMapper.insert(paymentStatusDTO);
    }
}
