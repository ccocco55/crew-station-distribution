package com.example.crewstation.payment.status;

import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.mapper.payment.status.PaymentStatusMapper;
import com.example.crewstation.repository.payment.status.PaymentStatusDAO;
import com.example.crewstation.repository.post.PostDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class DaoTest {
    @Autowired
    private PaymentStatusDAO paymentStatusDAO;

    @Test
    @Transactional
    public void testInsert() {
        PaymentStatusDTO paymentStatusDTO = new PaymentStatusDTO();
        paymentStatusDTO.setPurchaseId(1L);
        paymentStatusDTO.setMemberId(1L);
        paymentStatusDAO.save(paymentStatusDTO);
    }

}
