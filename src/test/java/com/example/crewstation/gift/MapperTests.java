package com.example.crewstation.gift;

import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.mapper.gift.GiftMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class MapperTests {
    @Autowired
    private GiftMapper giftMapper;
    @Autowired
    private GiftDTO giftDTO;

    @Test
    public void giftTest() {
        GiftDTO dto = new GiftDTO();
        List<GiftDTO> giftDTOS = giftMapper.getGift(4);
        log.info("giftDTOS = {}", giftDTOS);




    }
}
