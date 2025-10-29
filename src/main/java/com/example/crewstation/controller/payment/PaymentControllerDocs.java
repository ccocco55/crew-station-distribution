package com.example.crewstation.controller.payment;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.payment.PaymentResponseDTO;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.dto.reply.ReplyCriteriaDTO;
import com.example.crewstation.dto.reply.ReplyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

// http://localhost:10000/swagger-ui/index.html
@Tag(name = "Payment", description = "Payment API")
public interface PaymentControllerDocs {
    @Operation(summary = "판매 요청",
    description = "회원과 비회원이 판매 요청이 동작하는 부분",
    parameters = {
            @Parameter(name = "purchaseId",description = "기프트 게시글의 아이디가 들어온다"),
            @Parameter(name = "paymentStatusDTO",description = "비회원시 작성한 주소와 휴대전화번호가 들어온다"),
            @Parameter(name="userDetails",description = "회원시 회원의 정보가 들어온다.")
    })
    public ResponseEntity<PaymentResponseDTO> requestPayment(@PathVariable Long purchaseId,@RequestBody PaymentStatusDTO paymentStatusDTO, @AuthenticationPrincipal CustomUserDetails userDetails);


}
