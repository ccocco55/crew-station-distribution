package com.example.crewstation.controller.payment;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.exception.CannotDecreaseBelowZeroException;
import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.common.exception.SmsSendFailException;
import com.example.crewstation.dto.payment.PaymentDTO;
import com.example.crewstation.dto.payment.PaymentResponseDTO;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment/**")
@Slf4j
@RequiredArgsConstructor
public class PaymentRestController {
    private final PaymentService paymentService;

    @PostMapping("{purchaseId}")
    public ResponseEntity<PaymentResponseDTO> requestPayment(@PathVariable Long purchaseId,@RequestBody(required = false) PaymentStatusDTO paymentStatusDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try{

            PaymentResponseDTO message = paymentService.requestPayment(purchaseId, paymentStatusDTO, userDetails);
            log.info(message.toString());
            return ResponseEntity.ok().body(message);
        }catch (PostNotFoundException e){
            PaymentResponseDTO error = new PaymentResponseDTO();
            error.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }catch (SmsSendFailException | CannotDecreaseBelowZeroException e){
            PaymentResponseDTO error = new PaymentResponseDTO();
            error.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completePayment(@RequestBody PaymentDTO paymentDTO) {
        log.info("[PaymentRestController] 결제 요청 수신: {}", paymentDTO);
        paymentService.completePayment(paymentDTO.getPaymentStatusId(), paymentDTO);
        return ResponseEntity.ok("결제가 완료되었습니다.");
    }
}
