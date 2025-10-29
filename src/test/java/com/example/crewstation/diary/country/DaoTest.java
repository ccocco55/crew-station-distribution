package com.example.crewstation.diary.country;

import com.example.crewstation.common.enumeration.Secret;
import com.example.crewstation.domain.diary.DiaryVO;
import com.example.crewstation.domain.diary.country.DiaryCountryVO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.repository.diary.country.DiaryCountryDAO;
import com.example.crewstation.repository.post.PostDAO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Slf4j
public class DaoTest {
    @Autowired
    private DiaryCountryDAO diaryCountryDAO;
    @Autowired
    private PostDAO postDAO;
    @Autowired
    private DiaryDAO diaryDAO;

    @Test
    @Transactional
    public void testSave(){
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setMemberId(1L);
        purchaseDTO.setPostTitle("123");
        postDAO.savePost(purchaseDTO);
        DiaryVO diaryVO =DiaryVO.builder()
                .diarySecret(Secret.PUBLIC)
                .postId(purchaseDTO.getPostId())
                .build();
        diaryDAO.save(diaryVO);
        DiaryCountryVO diaryCountryVO = DiaryCountryVO.builder()
                .postId(purchaseDTO.getPostId())
                .countryId(1L)
                .build();
        diaryCountryDAO.save(diaryCountryVO);
    }
}
