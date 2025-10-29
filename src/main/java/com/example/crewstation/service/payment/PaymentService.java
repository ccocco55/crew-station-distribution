package com.example.crewstation.service.payment;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.domain.guest.GuestVO;
import com.example.crewstation.dto.payment.PaymentDTO;
import com.example.crewstation.dto.payment.PaymentResponseDTO;
import com.example.crewstation.dto.payment.status.PaymentCriteriaDTO;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import java.util.List;
import java.util.Map;

public interface PaymentService {
    public PaymentResponseDTO requestPayment(Long purchaseId,PaymentStatusDTO paymentStatusDTO, CustomUserDetails userDetails);

    public void completePayment(Long purchaseId, PaymentDTO paymentDTO);

    public List<PaymentCriteriaDTO> selectPayment(Search search, int size);

    public int countPayment(Search search);

    public PaymentCriteriaDTO getPaymentDetail(Long id);

    public Map<String, Object> getPaymentSummary(Search search);




    default GuestVO toVO(PaymentStatusDTO  paymentStatusDTO) {
        return GuestVO.builder()
                .memberId(paymentStatusDTO.getMemberId())
                .guestPhone(paymentStatusDTO.getMemberPhone())
                .guestPassword(paymentStatusDTO.getGuestPassword())
                .guestOrderNumber(paymentStatusDTO.getGuestOrderNumber())
                .addressDetail(paymentStatusDTO.getAddressDetail())
                .address(paymentStatusDTO.getAddress())
                .addressZipCode(paymentStatusDTO.getAddressZipCode())
                .build();
    }

}
