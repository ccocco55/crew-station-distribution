package com.example.crewstation.member.daoTests;

import com.example.crewstation.common.enumeration.Gender;
import com.example.crewstation.common.enumeration.MemberProvider;
import com.example.crewstation.common.enumeration.MemberRole;
import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.repository.member.MemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
public class MemberDAOTests {
    @Autowired
    public MemberDAO memberDAO;
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Test
    public void testInsert() {
        MemberVO memberVO = MemberVO.builder()
                .memberName("test1")
                .memberPhone("01011111111")
                .memberEmail("test1@gmail.com")
                .memberBirth("20000101")
                .memberGender(Gender.FEMALE)
                .memberPassword("1234")
                .memberProvider(MemberProvider.CREWSTATION)
                .memberRole(MemberRole.MEMBER)
                .build();

        memberDAO.save(memberVO);

    }

    @Test
    @Transactional
    public void testSaveGuest(){
        MemberDTO memberDTO = new MemberDTO();
        memberDAO.saveGuest(memberDTO);
    }

    @Test
    @Transactional
    public void snsJoinTest() {
        MemberVO memberVO = MemberVO.builder()
                .memberName("test")
                .memberPhone("test")
                .memberSocialEmail("test")
                .memberBirth("test")
                .memberGender(Gender.MALE)
                .memberProvider(MemberProvider.KAKAO)
                .memberRole(MemberRole.MEMBER)
                .build();

        memberDAO.saveSns(memberVO);
    }

    @Test
    public void snsSelectTest() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberSocialEmail("test123");

        memberDAO.findBySnsEmail("test123");
        log.info(String.valueOf(memberDAO.findBySnsEmail("test123")));
    }

    @Test
    @Transactional
    public void passwordUpdateTest() {
        memberDAO.updatePassword("test1@gmail.com", "1234");
    }

    @Test
    public void testfindSearchMember() {
        List<MemberDTO> test = memberDAO.findSearchMember("test");
        test.stream().map(MemberDTO::toString).forEach(log::info);
    }

    @Test
    public void testjoin() {
        MemberVO memberVO = MemberVO.builder()
                .memberName("test1")
                .memberPhone("01011111111")
                .memberEmail("test1@gmail.com")
                .memberBirth("20000101")
                .memberPassword(passwordEncoder.encode("test1234"))
                .memberProvider(MemberProvider.CREWSTATION)
                .memberRole(MemberRole.MEMBER)
                .build();

        memberDAO.save(memberVO);

    }
}
