package com.example.crewstation.purchase;

import com.example.crewstation.common.enumeration.DeliveryMethod;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.service.purchase.PurchaseService;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
public class ServiceTest {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PurchaseDTO purchaseDTO;

    @Test
    public void testGetPurchases(){
        Search search = new Search();
        search.setPage(2);
        search.setKeyword("test");
//        log.info("{}",);
        Criteria criteria = new Criteria(search.getPage(),1);
        log.info("{}",criteria.toString());
        log.info("testGetPurchases {}", purchaseService.getPurchases(search));
    }

    @Test
    public void testGetPurchase(){
        purchaseService.getPurchase(2L);
    }


    @Test
    @Transactional
    public void testWrite(){
        PurchaseDTO purchase = purchaseDTO;
        purchase.setPurchaseContent("test content");
        purchase.setPurchaseLimitTime(12);
        purchase.setPostTitle("test title");
        purchase.setPurchaseCountry("호주");
        purchase.setPurchaseProductCount(1);
        purchase.setPurchaseProductPrice("1000");
        purchase.setPurchaseDeliveryMethod(DeliveryMethod.DIRECT);
        purchase.setThumbnail(1L);
        purchase.setMemberId(1L);
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",                          // 필드 이름
                "test.png",                      // 파일명
                "image/png",                     // MIME 타입
                "dummy image content".getBytes() // 파일 내용
        );
        List<MultipartFile> multipartFiles = Arrays.asList(multipartFile);
        purchaseService.write(purchase,multipartFiles);
    }
    @Test
    @Transactional
    public void testUpdate(){
        PurchaseDTO purchase = purchaseDTO;
        purchase.setPurchaseContent("test content");
        purchase.setPurchaseLimitTime(12);
        purchase.setPostTitle("test title");
        purchase.setPurchaseCountry("호주");
        purchase.setPurchaseProductCount(1);
        purchase.setPurchaseProductPrice("1000");
        purchase.setPurchaseDeliveryMethod(DeliveryMethod.DIRECT);
        purchase.setThumbnail(1L);
        purchase.setPostId(1L);
        purchase.setMemberId(1L);
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",                          // 필드 이름
                "test.png",                      // 파일명
                "image/png",                     // MIME 타입
                "dummy image content".getBytes() // 파일 내용
        );
        Long[] id = new Long[]{1L,2L,3L,4L,5L,6L,7L,8L,9L};
        List<MultipartFile> multipartFiles = Arrays.asList(multipartFile);
        purchaseService.update(purchase,id,multipartFiles);
    }
}
