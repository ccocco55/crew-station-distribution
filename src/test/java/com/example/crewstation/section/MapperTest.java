package com.example.crewstation.section;

import com.example.crewstation.domain.post.section.PostSectionVO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.mapper.section.SectionMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    SectionMapper sectionMapper;

    @Test
    public void testSelectSectionsByPostId(){
        log.info("selectSectionsByPostId {}", sectionMapper.selectSectionsByPostId(3L));
    }

    @Test
    @Transactional
    public void testInsert(){
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setMemberId(1L);
        purchaseDTO.setPurchaseContent("test");
        purchaseDTO.setPostId(14L);
        sectionMapper.insert(purchaseDTO);
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
        sectionMapper.update(vo);
    }

    @Test
    @Transactional
    public void testDelete(){
        sectionMapper.delete(35L);
    }

    @Test
    public void testSelectSectionFileCount(){
        log.info("selectSectionFileCount {}", sectionMapper.selectSectionFileCount(3L));
    }
}
