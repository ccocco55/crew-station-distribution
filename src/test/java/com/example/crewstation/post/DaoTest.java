package com.example.crewstation.post;

import com.example.crewstation.domain.post.PostVO;
import com.example.crewstation.domain.purchase.PurchaseVO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.repository.post.PostDAO;
import com.example.crewstation.repository.purchase.PurchaseDAO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class DaoTest {
    @Autowired
    private PostDAO postDAO;

    @Test
    public void testSavePostReport(){
        postDAO.savePostReport(2L,1L);
    }

    @Test
    public void testIsActivePost(){
        log.info("testIsActivePost::::{}",postDAO.isActivePost(1L));
    }

    @Test
    @Transactional
    public void testInsertPost(){
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setMemberId(1L);
        purchaseDTO.setPostTitle("post title");
        postDAO.savePost(purchaseDTO);
        log.info("purchaseDTO postId:{}", purchaseDTO.getPostId());
    }

    @Test
    @Transactional
    public void testUpdate(){
        PostVO vo = PostVO.builder()
                .id(1L)
                .memberId(1L)
                .postTitle("test update")
                .build();
        postDAO.update(vo);
    }
}
