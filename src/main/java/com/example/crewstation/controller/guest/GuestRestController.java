package com.example.crewstation.controller.guest;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.dto.diary.LikedDiaryCriteriaDTO;
import com.example.crewstation.dto.diary.ReplyDiaryCriteriaDTO;
import com.example.crewstation.dto.guest.GuestOrderDetailDTO;
import com.example.crewstation.service.diary.DiaryService;
import com.example.crewstation.service.guest.GuestService;
import com.example.crewstation.util.ScrollCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/guest")
public class GuestRestController {

    private final GuestService guestService;

    // 주문번호로 단건 조회
    @GetMapping("/order-detail")
    public ResponseEntity<GuestOrderDetailDTO> getOrderDetail(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        String guestOrderNumber = customUserDetails.getGuestOrderNumber();
        log.info("GET /order-detail called by guestOrderNumber={}", guestOrderNumber);

        GuestOrderDetailDTO orderDetail = guestService.getOrderDetail(guestOrderNumber);
        return ResponseEntity.ok(orderDetail);
    }

    // 결제 상태 업데이트
    @PutMapping("/order/status")
    public ResponseEntity<Void> updatePaymentStatus(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam PaymentPhase paymentPhase) {

        String guestOrderNumber = customUserDetails.getGuestOrderNumber();
        log.info("PUT /order/status called by guestOrderNumber={}, phase={}", guestOrderNumber, paymentPhase);

        // 비회원 주문번호로 주문 상세 조회
        GuestOrderDetailDTO order = guestService.getOrderDetail(guestOrderNumber);
        if (order == null) {
            log.warn("Order not found: {}", guestOrderNumber);
            return ResponseEntity.notFound().build();
        }

        guestService.updatePaymentStatus(order.getPaymentStatusId(), paymentPhase);

        log.info("Order status updated for paymentStatusId={}, phase={}", order.getPaymentStatusId(), paymentPhase);
        return ResponseEntity.ok().build();
    }



}