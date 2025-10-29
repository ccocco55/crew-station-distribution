package com.example.crewstation.guest;

import com.example.crewstation.domain.guest.GuestVO;
import com.example.crewstation.dto.guest.GuestDTO;
import com.example.crewstation.dto.guest.GuestOrderDetailDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.repository.guest.GuestDAO;
import com.example.crewstation.repository.member.MemberDAO;
import com.example.crewstation.service.payment.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class DaoTest {
    @Autowired
    private GuestDAO guestDAO;
    @Autowired
    private MemberDAO memberDAO;
    @Autowired
    private PaymentService paymentService;

    @Test
    @Transactional
    public void testInsert() {
        MemberDTO memberDTO = new MemberDTO();
        memberDAO.saveGuest(memberDTO);
        PaymentStatusDTO paymentStatusDTO = new PaymentStatusDTO();
        paymentStatusDTO.setMemberId(memberDTO.getId());
        paymentStatusDTO.setGuestOrderNumber("test");
        paymentStatusDTO.setAddress("test");
        paymentStatusDTO.setMemberPhone("test");
        paymentStatusDTO.setAddressDetail("test");
        paymentStatusDTO.setAddressZipCode("test");
        GuestVO vo = paymentService.toVO(paymentStatusDTO);
        guestDAO.save(vo);
    }

    @Test
    public void testSelect() {
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setGuestPhone("test");
        guestDTO.setGuestOrderNumber("test");

        guestDAO.findGuest(guestDTO);
        log.info("guest {}", guestDAO.findGuest(guestDTO));
    }

    @Test
    void testFindOrderDetails() {
        Long guestId = 1L;

//        guestDAO.findOrderDetails(guestId);
//        log.info("guest {}", guestDAO.findOrderDetails(guestId));
    }


}
