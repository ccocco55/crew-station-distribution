package com.example.crewstation.repository.payment.status;


import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.dto.payment.status.PaymentCriteriaDTO;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.mapper.payment.status.PaymentStatusMapper;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
@Slf4j
public class PaymentStatusDAO {
    private final PaymentStatusMapper paymentStatusMapper;

    //    판매 요청 신청
    public void save(PaymentStatusDTO paymentStatusDTO) {
        paymentStatusMapper.insert(paymentStatusDTO);
    }

    // 결제 상태 ID로 조회 (추가)
    public PaymentStatusDTO findByPaymentStatusId(Long paymentStatusId) {
        return paymentStatusMapper.findByPaymentStatusId(paymentStatusId);
    }

    // 결제 상태 업데이트
    public void updatePaymentStatus(Long paymentStatusId, PaymentPhase paymentPhase) {
        paymentStatusMapper.updatePaymentStatus(paymentStatusId, paymentPhase);

    }

//    결제 목록 갯수
    public int countPayment(Search search){
        return paymentStatusMapper.countPayment(search);
    }

//    결제 목록
    public List<PaymentCriteriaDTO> adminPaymentList(Search search, Criteria criteria) {
        return paymentStatusMapper.selectPayment(search, criteria);
    }
//      결제 상세
    public PaymentCriteriaDTO selectPaymentDetail(Long id) {
        return paymentStatusMapper.selectPaymentDetail(id);
    }

//    결제 승인/취소 금액 합계
    public Map<String, Object> selectPaymentSummary(Search search) {
        return paymentStatusMapper.selectPaymentSummary(search);
    }

}
