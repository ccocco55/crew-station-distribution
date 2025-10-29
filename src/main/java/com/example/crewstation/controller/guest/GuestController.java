package com.example.crewstation.controller.guest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guest/**")
@Slf4j
public class GuestController {

    // 비회원 주문 상세 조회
    @GetMapping("/order-detail")
    public String loadMyActivitiesPage() {
        return "guest/order-detail";
    }
}