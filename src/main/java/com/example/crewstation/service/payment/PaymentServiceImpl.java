package com.example.crewstation.service.payment;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.common.exception.CannotDecreaseBelowZeroException;
import com.example.crewstation.common.exception.PostNotActiveException;
import com.example.crewstation.domain.guest.GuestVO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.payment.PaymentDTO;
import com.example.crewstation.dto.payment.PaymentResponseDTO;
import com.example.crewstation.dto.payment.status.PaymentCriteriaDTO;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.repository.alarm.AlarmDAO;
import com.example.crewstation.repository.guest.GuestDAO;
import com.example.crewstation.repository.member.MemberDAO;
import com.example.crewstation.repository.payment.PaymentDAO;
import com.example.crewstation.repository.payment.status.PaymentStatusDAO;
import com.example.crewstation.repository.post.PostDAO;
import com.example.crewstation.repository.purchase.PurchaseDAO;
import com.example.crewstation.service.sms.SmsService;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentDAO paymentDAO;
    private final PaymentStatusDAO paymentStatusDAO;
    private final AlarmDAO alarmDAO;
    private final PostDAO postDAO;
    private final MemberDAO memberDAO;
    private final GuestDAO guestDAO;
    private final SmsService smsService;
    private final PasswordEncoder passwordEncoder;
    private final PurchaseDAO purchaseDAO;
    private final RedisTemplate<String, PurchaseDTO> purchaseRedisTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogReturnStatus
    public PaymentResponseDTO requestPayment(Long purchaseId,PaymentStatusDTO paymentStatusDTO, CustomUserDetails userDetails) {

        String code = null;
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        String message = null;
        boolean isExist = postDAO.isActivePost(purchaseId);
        if(userDetails != null){
            paymentStatusDTO.setMemberId(userDetails.getId());
        }
        log.info("isExist={}", isExist);
        log.info("paymentStatusDTO={}", paymentStatusDTO.toString());

        if (!isExist) {
            throw new PostNotActiveException("이미 삭제된 상품입니다.");
        }
        
        if (paymentStatusDTO.isGuest()) {
//            멤버랑 게스트에 값 넣어주기
            MemberDTO memberDTO = new MemberDTO();
            memberDAO.saveGuest(memberDTO);
            paymentStatusDTO.setMemberId(memberDTO.getId());
            code = smsService.send(paymentStatusDTO.getMemberPhone());
//            code ="1234123412";
            paymentStatusDTO.setGuestOrderNumber(code);
            paymentStatusDTO.setGuestPassword(passwordEncoder.encode(paymentStatusDTO.getMemberPhone()));
            GuestVO vo = toVO(paymentStatusDTO);

            guestDAO.save(vo);

        } else if (paymentStatusDTO.getMemberId() == null) {
            message = "비회원입니다.";
            paymentResponseDTO.setMessage(message);
            paymentResponseDTO.setGuest(true);
            return paymentResponseDTO;
        }
        boolean shouldDecrease = purchaseDAO.updatePurchaseProductCount(purchaseId,-1);
        if(!shouldDecrease){
            throw new CannotDecreaseBelowZeroException("이미 품절된 상품입니다.");
        }

        int count = purchaseDAO.findPurchaseProductCount(purchaseId);
        paymentStatusDTO.setPurchaseId(purchaseId);
        paymentStatusDAO.save(paymentStatusDTO);
        alarmDAO.savePaymentAlarm(paymentStatusDTO.getId());
        message = "판매요청 완료되었습니다.";
        paymentResponseDTO.setMessage(message);

        if(purchaseRedisTemplate.opsForValue().get("purchase::purchases_" + purchaseId) !=null){
            purchaseRedisTemplate.delete("purchase::purchases_" + purchaseId);
        }
        if(redisTemplate.opsForValue().get("gifts") !=null){
            redisTemplate.delete("gifts");
        }
        paymentResponseDTO.setCount(count);
        return paymentResponseDTO;
    }

    @Transactional
    @Override
    public void completePayment(Long paymentStatusId, PaymentDTO paymentDTO) {

        // 결제 상태 조회
        PaymentStatusDTO status = paymentStatusDAO.findByPaymentStatusId(paymentStatusId);
        if (status == null) {
            throw new IllegalStateException("결제 상태 정보가 없습니다. paymentStatusId=" + paymentStatusId);
        }

        paymentDTO.setPaymentStatusId(status.getId());

        // 필수 값 검증
        if (paymentDTO.getPaymentAmount() == null || paymentDTO.getReceiptId() == null) {
            throw new IllegalArgumentException("결제 금액 또는 영수증 ID가 누락되었습니다.");
        }

        // 결제 내역 저장
        paymentDAO.insertPayment(paymentDTO);

        // 결제 상태 업데이트
        paymentStatusDAO.updatePaymentStatus(paymentStatusId, PaymentPhase.SUCCESS);
    }


    @Override
    public List<PaymentCriteriaDTO> selectPayment(Search search, int size) {
        int total = paymentStatusDAO.countPayment(search);
        int page  = Math.max(1, search.getPage());
        Criteria criteria = new Criteria(page, total, 16, 10);
        return paymentStatusDAO.adminPaymentList(search, criteria);


    }

    @Override
    public int countPayment(Search search) {
        return paymentStatusDAO.countPayment(search);
    }

    @Override
    public PaymentCriteriaDTO getPaymentDetail(Long id) {
        return paymentStatusDAO.selectPaymentDetail(id);
    }

    @Override
    public Map<String, Object> getPaymentSummary(Search search) {
        Map<String, Object> raw = paymentStatusDAO.selectPaymentSummary(search);
        long approved = ((Number) raw.getOrDefault("approvedAmount", 0L)).longValue();
        long canceled = ((Number) raw.getOrDefault("canceledAmount", 0L)).longValue();

        return Map.of("approvedAmount", approved, "canceledAmount", canceled);

    }


}
