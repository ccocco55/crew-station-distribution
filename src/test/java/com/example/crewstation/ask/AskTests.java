package com.example.crewstation.ask;

import com.example.crewstation.domain.ask.AskVO;
import com.example.crewstation.mapper.ask.AskMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AskTests {
    @Autowired
    private AskMapper askMapper;

    @Test
    public void test(){
        AskVO askVO = new AskVO();
        askVO.setInquiryTitle("gggg");
        askVO.setInquiryContent("pppp");
        askVO.setMemberId(1L);
        askMapper.insert(askVO);
    }
}
