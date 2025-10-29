package com.example.crewstation.gift;

import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.mapper.gift.GiftMapper;
import com.example.crewstation.service.gift.GiftService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class ServiceTests {
    @Autowired
    private GiftService giftService;

    public void getGiftTest(){
        GiftDTO dto = new GiftDTO();
        List<GiftDTO> giftDTOs = giftService.getGift(4);
        log.info("giftDTOs = {}", giftDTOs);
    }
}
