package com.example.crewstation.repository.member;

import com.example.crewstation.domain.file.member.MemberFileVO;
import com.example.crewstation.mapper.member.MemberFileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberFileDAO {
    private final MemberFileMapper memberFileMapper;
    //    회원가입 시 주소
    public void save(MemberFileVO memberFileVO) {
        memberFileMapper.insert(memberFileVO);
    }

}
