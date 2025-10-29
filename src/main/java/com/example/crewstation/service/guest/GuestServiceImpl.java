package com.example.crewstation.service.guest;

import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.common.exception.MemberLoginFailException;
import com.example.crewstation.dto.guest.GuestDTO;
import com.example.crewstation.dto.guest.GuestOrderDetailDTO;
import com.example.crewstation.repository.guest.GuestDAO;
import com.example.crewstation.repository.payment.status.PaymentStatusDAO;
import com.example.crewstation.service.payment.PaymentService;
import com.example.crewstation.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestServiceImpl implements GuestService {
    private final GuestDAO guestDAO;
    private final PaymentStatusDAO paymentStatusDAO;
    private final S3Service s3Service;

    @Override
    public GuestDTO login(GuestDTO guestDTO) {
        return guestDAO.findGuest(guestDTO).orElseThrow(MemberLoginFailException::new);
    }

    @Override
    public GuestOrderDetailDTO getOrderDetail(String guestOrderNumber) {
        GuestOrderDetailDTO orderDetail = guestDAO.findOrderDetail(guestOrderNumber)
                .orElseThrow(() -> new RuntimeException("주문 내역을 찾을 수 없습니다. 주문번호=" + guestOrderNumber));

        // S3 프리사인 URL 변환
        if (orderDetail.getMainImage() != null && !orderDetail.getMainImage().isBlank()) {
            String preSignedUrl = s3Service.getPreSignedUrl(orderDetail.getMainImage(), Duration.ofMinutes(5));
            orderDetail.setMainImage(preSignedUrl);
        }

        return orderDetail;
    }

    // 비회원이 주문번호로 조회한 후 결제 상태 변경
    @Override
    public void updatePaymentStatus(Long paymentStatusId, PaymentPhase paymentPhase) {
        paymentStatusDAO.updatePaymentStatus(paymentStatusId, paymentPhase);
    }
}
