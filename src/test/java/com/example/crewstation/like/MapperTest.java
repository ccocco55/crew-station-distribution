package com.example.crewstation.like;

import com.example.crewstation.domain.guest.GuestVO;
import com.example.crewstation.domain.like.LikeVO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.guest.GuestDTO;
import com.example.crewstation.dto.like.LikeDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.mapper.guest.GuestMapper;
import com.example.crewstation.mapper.like.LikeMapper;
import com.example.crewstation.repository.like.LikeDAO;
import com.example.crewstation.repository.member.MemberDAO;
import com.example.crewstation.service.payment.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class MapperTest {

    @Autowired
    LikeMapper likeMapper;
    @Transactional
    @Test
    public void testInsertLike(){
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setMemberId(1L);
        likeDTO.setPostId(1L);
        likeMapper.insertLike(likeDTO);

    }
    @Transactional
    @Test
    public void testDeleteLike(){
        likeMapper.deleteLike(1L);
    }

    @Test
    public void testExistLikeByPostIdAndMemberId(){
        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setPostId(1L);
        diaryDTO.setMemberId(1L);
        Long check = likeMapper.existsLikeByPostIdAndMemberId(diaryDTO);
        log.info("check existsLikeByPostIdAndMemberId: {}", check);
    }
}
