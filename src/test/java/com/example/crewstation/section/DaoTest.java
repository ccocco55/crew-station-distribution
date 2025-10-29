package com.example.crewstation.section;

import com.example.crewstation.domain.post.section.PostSectionVO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.repository.section.SectionDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class DaoTest {
    @Autowired
    private SectionDAO sectionDAO;

    @Test
    public void testFindSectionsByPostId(){
        log.info("findSectionsByPostId {}",sectionDAO.findSectionsByPostId(2L));
    }

    @Test
    @Transactional
    public void testSave(){
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setMemberId(1L);
        purchaseDTO.setPurchaseContent("test");
        purchaseDTO.setPostId(14L);
        sectionDAO.save(purchaseDTO);
        log.info("purchaseDTO postSectionId:{}", purchaseDTO.getPostSectionId());
    }

    @Test
    @Transactional
    public void testUpdate(){
        PostSectionVO vo = PostSectionVO.builder()
                .postContent("수정 내용")
                .id(33L)
                .postId(25L)
                .build();
        sectionDAO.update(vo);
    }

    @Test
    @Transactional
    public void testDelete(){
        sectionDAO.delete(35L);
    }

    @Test
    public void testFindSectionFileCount(){
        log.info("findSectionFileCount {}", sectionDAO.findSectionFileCount(3L));
    }

}
