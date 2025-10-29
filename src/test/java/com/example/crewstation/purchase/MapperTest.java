package com.example.crewstation.purchase;

import com.example.crewstation.common.enumeration.DeliveryMethod;
import com.example.crewstation.common.enumeration.Type;
import com.example.crewstation.domain.purchase.PurchaseVO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.mapper.purchase.PurchaseMapper;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    private PurchaseMapper purchaseMapper;

    @Test
    public void testSelectAllByKeyWord() {
        Search search = new Search();
        search.setKeyword("호주");
        Criteria criteria = new Criteria(1, 10);

        log.info("testSelectAllByKeyWord {}", purchaseMapper.selectAllByKeyWord(criteria, search));
    }

    @Test
    public void testSelectCountAllByKeyWord() {
        Search search = new Search();
        search.setKeyword("호주");

        log.info("testSelectAllByKeyWord {}", purchaseMapper.selectCountAllByKeyWord(search));
    }

    @Test
    public void testSelectByPostId() {
        Optional<PurchaseDTO> purchaseDetailDTO = purchaseMapper.selectByPostId(1L);
        assertThat(purchaseDetailDTO).isPresent();
//        log.info("testSelectByPostId {}", );
    }
    @Test
    public void testUpdateReadCount(){
        purchaseMapper.updateReadCount(1L);

    }

    @Test
    @Transactional
    public void testInsert(){
        PurchaseVO vo = PurchaseVO.builder()
                .postId(19L)
                .deliveryMethod(DeliveryMethod.DIRECT)
                .purchaseCountry("호주")
                .purchaseLimitTime(12)
                .purchaseProductPrice(1000)
                .purchaseProductCount(5)
                .build();
        purchaseMapper.insert(vo);
    }

    @Test
    @Transactional
    public void testUpdate(){
        PurchaseVO vo = PurchaseVO.builder()
                .postId(19L)
                .deliveryMethod(DeliveryMethod.DIRECT)
                .purchaseCountry("호주")
                .purchaseLimitTime(12)
                .purchaseProductPrice(1000)
                .purchaseProductCount(5)
                .build();
        purchaseMapper.update(vo);
    }

    @Test
    @Transactional
    public void testUpdatePurchaseProductCount(){

        assertThat(purchaseMapper.updatePurchaseProductCount(1L,-1)).isNull();
    }
}
