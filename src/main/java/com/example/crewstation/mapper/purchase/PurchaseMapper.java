package com.example.crewstation.mapper.purchase;

import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.domain.purchase.PurchaseVO;
import com.example.crewstation.dto.member.MyPurchaseDetailDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.dto.purchase.PurchaseListDTO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PurchaseMapper {

//  검색결과에 따른 기프트 목록 보여주기
    public List<PurchaseDTO> selectAllByKeyWord(@Param("criteria") Criteria criteria, @Param("search") Search search);
//  검색 결과에 따른 기프트 수 보여주기
    public int selectCountAllByKeyWord(@Param("search") Search search);

//  기프트 상세 보기
    public Optional<PurchaseDTO> selectByPostId(Long postId);

//  조회 수 증가
    public void updateReadCount(Long postId);

//  기프트 추가
    public void insert(PurchaseVO purchaseVO);

//  상품 수정
    public void update(PurchaseVO purchaseVO);

// 구매 상세 조회
    public Optional<PurchaseDetailDTO> selectPurchaseDetailByPostId(Long postId);

    // 나의 구매내역 목록 조회
    public List<PurchaseListDTO> selectPurchaseList(@Param("memberId") Long memberId, @Param("criteria") ScrollCriteria scrollcriteria, @Param("search") Search search);


    // 전체 개수 조회 (페이징)
    public int selectTotalCount(@Param("memberId") Long memberId, @Param("search") Search search);

    // 회원 주문 상세 조회
    public MyPurchaseDetailDTO selectMemberOrderDetails(@Param("memberId") Long memberId, @Param("paymentStatusId") Long paymentStatusId);


//  판매 요청 시 상품 개수 줄이기
    public boolean updatePurchaseProductCount(@Param("postId") Long postId, @Param("count") int count);

    public int selectPurchaseProductCount(Long postId);
}
