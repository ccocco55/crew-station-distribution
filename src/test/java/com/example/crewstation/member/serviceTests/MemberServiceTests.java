package com.example.crewstation.member.serviceTests;

import com.example.crewstation.common.enumeration.Gender;
import com.example.crewstation.common.enumeration.MemberProvider;
import com.example.crewstation.common.enumeration.MemberRole;
import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.member.MySaleListDTO;
import com.example.crewstation.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
public class MemberServiceTests {
    @Autowired
    MemberService memberService;

    @Test
    public void joinTest(){
        MemberVO memberVO = MemberVO.builder()
                .memberName("test2")
                .memberPhone("01012341234")
                .memberEmail("test2@gmail.com")
                .memberBirth("20000101")
                .memberGender(Gender.FEMALE)
                .memberPassword("1234")
                .memberProvider(MemberProvider.CREWSTATION)
                .memberRole(MemberRole.MEMBER)
                .build();

    }

    @Test
    public void emailTest(){
        boolean check =  memberService.checkEmail("test@gmail.com");

        log.info(String.valueOf(check));
    }

    @Test
    @Transactional
    public void passwordUpdateTest() {
        memberService.resetPassword("test@gmail.com", "1234");
    }

    @Test
    public void testSearchMember(){
        List<MemberDTO> test = memberService.searchMember("test");
        test.stream().map(MemberDTO::toString).forEach(log::info);
    }
//
//    @Test
//    void getMySaleListTest() {
//        Long memberId = 1L;
//        List<MySaleListDTO> saleList = memberService.getMySaleList(memberId);
//
//        saleList.forEach(System.out::println);
//    }
}
