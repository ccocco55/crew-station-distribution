package com.example.crewstation.purchase;

import com.example.crewstation.common.enumeration.DeliveryMethod;
import com.example.crewstation.domain.purchase.PurchaseVO;
import com.example.crewstation.mapper.purchase.PurchaseMapper;
import com.example.crewstation.repository.purchase.PurchaseDAO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class DaoTest {
    @Autowired
    private PurchaseDAO purchaseDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testFindAllByKeyWord() {
        Search search = new Search();
        search.setKeyword("호주");
        Criteria criteria = new Criteria(1,2);
        log.info("findAllByKeyWord {}", purchaseDAO.findAllByKeyWord(criteria, search));
        log.info("asd {}",passwordEncoder.encode("1234"));

    }
    @Test
    public void testFindCountAllByKeyWord() {
        Search search = new Search();
        search.setKeyword("호주");
        log.info("findAllByKeyWord {}", purchaseDAO.findCountAllByKeyWord(search));

    }
    @Test
    public void testFindByPostId(){
        log.info("testFindByPostId {}", purchaseDAO.findByPostId(3L));
    }

    @Test
    public void testIncreaseReadCount(){
        purchaseDAO.increaseReadCount(1L);

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
        purchaseDAO.save(vo);
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
        purchaseDAO.update(vo);
    }

    @Test
//    @Transactional
    public void testUpdatePurchaseProductCount(){
        int a = purchaseDAO.updatePurchaseProductCount(1L,-1);
        log.info("updatePurchaseProductCount {}", a);
    }
}
