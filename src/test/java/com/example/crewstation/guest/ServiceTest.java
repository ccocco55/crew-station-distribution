package com.example.crewstation.guest;

import com.example.crewstation.dto.guest.GuestDTO;
import com.example.crewstation.service.guest.GuestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ServiceTest {
    @Autowired
    private GuestService guestService;

    @Test
    public void testSelect() {
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setGuestPhone("test");
        guestDTO.setGuestOrderNumber("test");

        guestService.login(guestDTO);
        log.info("guest {}", guestService.login(guestDTO));
    }

}
