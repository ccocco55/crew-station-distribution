package com.example.crewstation.member.mapperTests;

import com.example.crewstation.domain.file.member.MemberFileVO;
import com.example.crewstation.mapper.member.MemberFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MemberFileMapperTests {
    @Autowired
    private MemberFileMapper memberFileMapper;

    @Test
    public void testInsertFile() {
        MemberFileVO memberFileVO = MemberFileVO.builder()
                .fileId(1L)
                .memberId(9L)
                .build();
        memberFileMapper.insert(memberFileVO);
    }
}
