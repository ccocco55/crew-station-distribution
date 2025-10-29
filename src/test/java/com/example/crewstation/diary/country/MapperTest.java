package com.example.crewstation.diary.country;

import com.example.crewstation.common.enumeration.Secret;
import com.example.crewstation.domain.diary.DiaryVO;
import com.example.crewstation.domain.diary.country.DiaryCountryVO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.mapper.diary.DiaryMapper;
import com.example.crewstation.mapper.diary.country.DiaryCountryMapper;
import com.example.crewstation.mapper.post.PostMapper;
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
public class MapperTest {
    @Autowired
    private DiaryCountryMapper diaryCountryMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private DiaryMapper diaryMapper;

    @Test
    @Transactional
    public void testInsert() {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setMemberId(1L);
        purchaseDTO.setPostTitle("123");
        postMapper.insert(purchaseDTO);
        DiaryVO diaryVO =DiaryVO.builder()
                .diarySecret(Secret.PUBLIC)
                .postId(purchaseDTO.getPostId())
                .build();
        diaryMapper.insert(diaryVO);
        DiaryCountryVO diaryCountryVO = DiaryCountryVO.builder()
                .postId(purchaseDTO.getPostId())
                .countryId(1L)
                .build();
        diaryCountryMapper.insert(diaryCountryVO);
    }
}
