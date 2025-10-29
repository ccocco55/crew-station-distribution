package com.example.crewstation.service.guest;

import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.dto.guest.GuestDTO;
import com.example.crewstation.dto.guest.GuestOrderDetailDTO;

import java.util.List;

public interface GuestService {
//    로그인
    public GuestDTO login(GuestDTO guestDTO);
    
//  상세 조회
    public GuestOrderDetailDTO getOrderDetail(String guestOrderNumber);

//  결제 상태 업데이트
    public void updatePaymentStatus(Long paymentStatusId, PaymentPhase paymentPhase);

}
