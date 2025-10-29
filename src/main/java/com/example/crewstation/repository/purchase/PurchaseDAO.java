package com.example.crewstation.repository.purchase;

import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.domain.purchase.PurchaseVO;
import com.example.crewstation.dto.member.MyPurchaseDetailDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.dto.purchase.PurchaseListDTO;
import com.example.crewstation.mapper.purchase.PurchaseMapper;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PurchaseDAO {
    private final PurchaseMapper purchaseMapper;

    //  검색결과에 따른 기프트 목록 보여주기
    public List<PurchaseDTO> findAllByKeyWord(Criteria criteria,Search search) {
        return purchaseMapper.selectAllByKeyWord(criteria, search);
    }
    //  검색 결과에 따른 기프트 수 보여주기
    public int findCountAllByKeyWord(Search search) {
        return purchaseMapper.selectCountAllByKeyWord(search);
    }
    //  기프트 상세 보기
    public Optional<PurchaseDTO> findByPostId(Long postId){
        return purchaseMapper.selectByPostId(postId);
    }
//  조회 수 증가
    public void increaseReadCount(Long postId) {
        purchaseMapper.updateReadCount(postId);
    }
//  기프트 추가
    public void save(PurchaseVO purchaseVO) {
        purchaseMapper.insert(purchaseVO);
    }
    //  상품 수정
    public void update(PurchaseVO purchaseVO){
        purchaseMapper.update(purchaseVO);
    };

    // 구매 상세
    public Optional<PurchaseDetailDTO> findByPurchaseDetail(Long postId){
        return purchaseMapper.selectPurchaseDetailByPostId(postId);
    }

    //  구매 내역 목록 조회
    public List<PurchaseListDTO> selectPurchaseList(Long memberId, ScrollCriteria scrollcriteria, Search search) {
        return purchaseMapper.selectPurchaseList(memberId, scrollcriteria, search);
    }

    //  전체 개수 조회
    public int selectTotalCount(Long memberId, Search search) {
        return purchaseMapper.selectTotalCount(memberId, search);
    }
    // 나의 구매 내역 상세 조회
    public MyPurchaseDetailDTO selectMemberOrderDetails(Long memberId, Long paymentId) {
        return purchaseMapper.selectMemberOrderDetails(memberId, paymentId);
    }


    //  판매 요청 시 상품 개수 줄이기
    public boolean updatePurchaseProductCount(Long postId,int count){
        return purchaseMapper.updatePurchaseProductCount(postId,count);
    }

    public int findPurchaseProductCount(Long postId){
        return purchaseMapper.selectPurchaseProductCount(postId);
    }
}
