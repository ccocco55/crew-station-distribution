package com.example.crewstation.diary.diary.path;

import com.example.crewstation.common.enumeration.Secret;
import com.example.crewstation.domain.diary.DiaryVO;
import com.example.crewstation.domain.diary.diary.path.DiaryDiaryPathVO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.mapper.diary.DiaryMapper;
import com.example.crewstation.mapper.diary.diary.path.DiaryDiaryPathMapper;
import com.example.crewstation.mapper.post.PostMapper;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.repository.diary.diary.path.DiaryDiaryPathDAO;
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
public class MapperTest {
    @Autowired
    private DiaryDiaryPathMapper diaryDiaryPathMapper;
    @Autowired
    private PostDAO postMapper;
    @Autowired
    private DiaryDAO diaryMapper;

    @Test
    @Transactional
    public void testSave(){
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setMemberId(1L);
        purchaseDTO.setPostTitle("123");
        postMapper.savePost(purchaseDTO);
        DiaryVO diaryVO =DiaryVO.builder()
                .diarySecret(Secret.PUBLIC)
                .postId(purchaseDTO.getPostId())
                .build();
        diaryMapper.save(diaryVO);
        DiaryDiaryPathVO diaryDiaryPathVO = DiaryDiaryPathVO.builder()
                .postId(purchaseDTO.getPostId())
                .diaryPathId(1L)
                .build();
        diaryDiaryPathMapper.insert(diaryDiaryPathVO);
    }
}
