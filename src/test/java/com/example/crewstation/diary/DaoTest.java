package com.example.crewstation.diary;

import com.example.crewstation.common.enumeration.Secret;
import com.example.crewstation.domain.diary.DiaryVO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.repository.post.PostDAO;
import com.example.crewstation.repository.purchase.PurchaseDAO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Slf4j
public class DaoTest {
    @Autowired
    private DiaryDAO diaryDAO;
    @Autowired
    private PostDAO postDAO;
    @Test
    public void testFindLikedDiaries() {
        Long memberId = 1L;
        ScrollCriteria criteria = new ScrollCriteria(1,17);
        criteria.setSize(3);
        criteria.setOffset(0);

        List<LikedDiaryDTO> diaries = diaryDAO.findDiariesLikedByMemberId(memberId, criteria);

        log.info("FindLikedDiaries size = {}", diaries.size());
        diaries.forEach(diary -> log.info("Diary: {}", diary));
    }

    @Test
    public void testCountLikedDiaries() {
        Long memberId = 1L;
        int count = diaryDAO.countDiariesLikedByMemberId(memberId);
    }

    @Test
    public void testSelectAllByKeyword(){
        Search search = new Search();
        search.setKeyword("호주");
        Criteria criteria = new Criteria(1,2);
        String order = "diary_like_count";
        List<DiaryDTO> diaryDTOS = diaryDAO.findAllByKeyword(criteria, search);
        diaryDTOS.stream().map(DiaryDTO::toString).forEach(log::info);
    }

    @Test
    public void testSelectCountAllByKeyword(){
        Search search = new Search();
        search.setKeyword("");
        search.setCategory("null");
        String order = "diary_like_count";
        int count = diaryDAO.findCountAllByKeyword(search);
        log.info("{}",count);
    }

    @Test
    @Transactional
    public void testChangeLikeCount(){
        diaryDAO.changeLikeCount(+1,30L);
    }

    @Test
    @Transactional
    public void testInsert(){
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setMemberId(1L);
        purchaseDTO.setPostTitle("123");
        postDAO.savePost(purchaseDTO);
        log.info("{}",purchaseDTO.getPostId());
        DiaryVO diaryVO =DiaryVO.builder()
                .diarySecret(Secret.PUBLIC)
                .postId(purchaseDTO.getPostId())
                .build();
        diaryDAO.save(diaryVO);
    }


    @Test
    public void testFindByPostId(){
        Optional<DiaryDTO> byPostId = diaryDAO.findByPostId(53L);
        assertThat(byPostId).isPresent();

    }
}
