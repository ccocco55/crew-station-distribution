package com.example.crewstation.service.purchase;

import com.example.crewstation.common.enumeration.PaymentPhase;
import com.example.crewstation.domain.file.section.FilePostSectionVO;
import com.example.crewstation.domain.post.PostVO;
import com.example.crewstation.domain.post.section.PostSectionVO;
import com.example.crewstation.domain.purchase.PurchaseVO;
import com.example.crewstation.dto.file.section.FilePostSectionDTO;
import com.example.crewstation.dto.member.MyPurchaseDetailDTO;
import com.example.crewstation.dto.payment.status.PaymentCriteriaDTO;
import com.example.crewstation.dto.purchase.PurchaseCriteriaDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.dto.purchase.PurchaseListCriteriaDTO;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
// 무한 스크롤 기프트 목록들 가져오기
    public PurchaseCriteriaDTO getPurchases(Search search);

// 기프트 상품 상세 내용 가져오기
    public PurchaseDTO getPurchase(Long postId);

// 기프트 상품 작성하기
    public void write(PurchaseDTO purchaseDTO,List<MultipartFile> files);


//  기프트 상품 업데이트
    public PurchaseDTO update(PurchaseDTO purchaseDTO,Long[] deleteFiles,List<MultipartFile> multipartFiles);

//  기프트 삭제
    public void softDelete(Long id);

//  구매내역 조회
    public PurchaseListCriteriaDTO getPurchaseListByMemberId(Long memberId, ScrollCriteria scrollcriteria, Search search);

//  나의 구매내역 상세 조회
    public MyPurchaseDetailDTO getMemberOrderDetails(Long memberId, Long paymentStatusId);

    // 결제 상태 업데이트
    public void updatePaymentStatus(Long paymentStatusId, PaymentPhase paymentPhase);

    default PostSectionVO toPostSectionVO(PurchaseDTO purchaseDTO){
        return PostSectionVO.builder()
                .id(purchaseDTO.getThumbnail())
                .createdDatetime(purchaseDTO.getCreatedDatetime())
                .updatedDatetime(purchaseDTO.getUpdatedDatetime())
                .postId(purchaseDTO.getPostId())
                .postContent(purchaseDTO.getPurchaseContent())
                .build();
    }
    default FilePostSectionVO toSectionFileVO(FilePostSectionDTO sectionFileDTO) {
        return FilePostSectionVO.builder()
                .fileId(sectionFileDTO.getFileId())
                .postSectionId(sectionFileDTO.getPostSectionId())
                .imageType(sectionFileDTO.getImageType())
                .createdDatetime(sectionFileDTO.getCreateDatetime())
                .updatedDatetime(sectionFileDTO.getUpdateDatetime())
                .build();
    }

    default PurchaseVO toPurchaseVO(PurchaseDTO purchaseDTO) {
        return PurchaseVO.builder()
                .postId(purchaseDTO.getPostId())
                .purchaseCountry(purchaseDTO.getPurchaseCountry())
                .createdDatetime(purchaseDTO.getCreatedDatetime())
                .updatedDatetime(purchaseDTO.getUpdatedDatetime())
                .deliveryMethod(purchaseDTO.getPurchaseDeliveryMethod())
                .purchaseLimitTime(purchaseDTO.getPurchaseLimitTime())
                .purchaseProductCount(purchaseDTO.getPurchaseProductCount())
                .purchaseProductPrice(purchaseDTO.getPrice())
                .build();
    }
    default PostVO toPostVO(PurchaseDTO purchaseDTO) {
        return PostVO.builder()
                .id(purchaseDTO.getPostId())
                .createdDatetime(purchaseDTO.getCreatedDatetime())
                .updatedDatetime(purchaseDTO.getUpdatedDatetime())
                .postTitle(purchaseDTO.getPostTitle())
                .memberId(purchaseDTO.getMemberId())
                .build();
    }

}
