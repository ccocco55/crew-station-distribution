package com.example.crewstation.controller.mypage;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.dto.member.ModifyDTO;
import com.example.crewstation.dto.member.MyPurchaseDetailDTO;
import com.example.crewstation.dto.member.MySaleDetailDTO;
import com.example.crewstation.service.member.MemberService;
import com.example.crewstation.service.purchase.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/mypage/**")
public class MypageRestController {

    private final MemberService memberService;
    private final PurchaseService purchaseService;

    // 구매 상세 조회
    @GetMapping("/purchase-detail/{postId}")
    public ResponseEntity<MyPurchaseDetailDTO> getPurchaseDetail(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long postId) {

        Long memberId = customUserDetails.getId();
        log.info("GET /purchase-detail called by memberId={}, postId={}", memberId, postId);

        MyPurchaseDetailDTO orderDetail = purchaseService.getMemberOrderDetails(memberId, postId);

        if (orderDetail == null) {
            log.warn("Order not found for memberId={}, postId={}", memberId, postId);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orderDetail);
    }

    // 결제 상태 업데이트
    @PutMapping("/purchase-detail/{paymentStatusId}/status")
    public ResponseEntity<Void> updatePaymentStatus(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long paymentStatusId,
            @RequestParam PaymentPhase paymentPhase) {

        Long memberId = customUserDetails.getId();
        log.info("PUT /purchase-detail/{}/status called by memberId={}, phase={}", paymentStatusId, memberId, paymentPhase);

        MyPurchaseDetailDTO order = purchaseService.getMemberOrderDetails(memberId, paymentStatusId);
        if (order == null) {
            log.warn("Order not found for memberId={}, paymentStatusId={}", memberId, paymentStatusId);
            return ResponseEntity.notFound().build();
        }

        purchaseService.updatePaymentStatus(paymentStatusId, paymentPhase);
        log.info("Payment status updated for paymentStatusId={}, newStatus={}", paymentStatusId, paymentPhase);
        return ResponseEntity.ok().build();
    }


    //   판매 상태 업데이트
    @PutMapping("/status/{paymentStatusId}")
    public ResponseEntity<Void> updateSaleStatus(
            @PathVariable Long paymentStatusId,
            @RequestParam String paymentPhase,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long memberId = customUserDetails.getId();
        log.info("PUT /api/mypage/status/{} by memberId={} with phase={}",
                paymentStatusId, memberId, paymentPhase);

        // 서비스 호출
        memberService.updateSaleStatus(memberId, paymentStatusId, PaymentPhase.valueOf(paymentPhase));

        return ResponseEntity.ok().build();
    }

    //  마이페이지 - 판매 상세 조회
    @GetMapping("/sale-detail/{paymentStatusId}")
    public ResponseEntity<MySaleDetailDTO> getMySaleDetail(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("paymentStatusId") Long paymentStatusId) {

        Long sellerId = customUserDetails.getId();
        log.info("판매 상세 요청 - sellerId={}, paymentStatusId={}", sellerId, paymentStatusId);

        MySaleDetailDTO detail = memberService.getSellerOrderDetails(sellerId, paymentStatusId);

        if (detail == null) {
            log.warn("판매 상세 정보 없음 - sellerId={}, paymentStatusId={}", sellerId, paymentStatusId);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(detail);
    }

    // 마이페이지 - 내 정보 조회
    @GetMapping("/modify/info")
    public ResponseEntity<ModifyDTO> getMemberInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ModifyDTO dto = memberService.getMemberInfo(customUserDetails);
        return ResponseEntity.ok(dto);
    }

}