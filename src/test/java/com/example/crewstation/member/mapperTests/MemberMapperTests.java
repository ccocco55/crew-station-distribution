package com.example.crewstation.member.mapperTests;

import com.example.crewstation.common.enumeration.Gender;
import com.example.crewstation.common.enumeration.MemberProvider;
import com.example.crewstation.common.enumeration.MemberRole;
import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.mapper.member.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
public class MemberMapperTests {
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    public void joinTest() {
        MemberVO memberVO = MemberVO.builder()
                .memberName("test")
                .memberPhone("01012341234")
                .memberEmail("test@gmail.com")
                .memberBirth("20000101")
                .memberGender(Gender.FEMALE)
                .memberPassword("1234")
                .memberProvider(MemberProvider.CREWSTATION)
                .memberRole(MemberRole.MEMBER)
                .build();

        memberMapper.insert(memberVO);
    }

    @Test
    public void emailTest() {
        boolean check = memberMapper.selectEmail("test@gmail.com");

        log.info(String.valueOf(check));
    }

    @Test
    public void testInsertGuest() {
        MemberDTO memberDTO = new MemberDTO();
        memberMapper.insertGuest(memberDTO);
    }

    @Test
    @Transactional
    public void snsJoinTest() {
        MemberVO memberVO = MemberVO.builder()
                .memberName("test123")
                .memberPhone("test123")
                .memberSocialEmail("test123")
                .memberBirth("test123")
                .memberGender(Gender.MALE)
                .memberProvider(MemberProvider.KAKAO)
                .memberRole(MemberRole.MEMBER)
                .build();

        memberMapper.insertSns(memberVO);
    }

    @Test
    @Transactional
    public void snsSelectTest() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberSocialEmail("test123");

        memberMapper.selectMemberBySnsEmail("test123");
        log.info(String.valueOf(memberMapper.selectMemberBySnsEmail("test123")));
    }

    @Test
    @Transactional
    public void passwordUpdateTest() {
        memberMapper.updatePassword("test@gmail.com", "1234");
    }

    @Test
    public void testSelectSearchMember(){
        List<MemberDTO> test = memberMapper.selectSearchMember("test");
        test.stream().map(MemberDTO::toString).forEach(log::info);
    }
}
