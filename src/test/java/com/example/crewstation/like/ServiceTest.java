package com.example.crewstation.like;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.guest.GuestDTO;
import com.example.crewstation.dto.like.LikeDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.service.guest.GuestService;
import com.example.crewstation.service.like.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class ServiceTest {
    @Autowired
    private LikeService likeService;

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void testLike() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(1L);
        CustomUserDetails userDetails = new CustomUserDetails(memberDTO);
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setPostId(30L);
        likeDTO.setMemberId(1L);
        likeService.like(likeDTO,userDetails);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void testUnLike() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(1L);
        CustomUserDetails userDetails = new CustomUserDetails(memberDTO);
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(1L);
        likeDTO.setPostId(30L);
        likeDTO.setMemberId(1L);
        likeService.unlike(1L,userDetails);
    }

}
