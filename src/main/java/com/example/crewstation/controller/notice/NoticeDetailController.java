package com.example.crewstation.controller.notice;

import com.example.crewstation.domain.notice.NoticeDetailVO;
import com.example.crewstation.service.notice.NoticeDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeDetailController {

    private final NoticeDetailService noticeDetailService;

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        NoticeDetailVO noticeDetail = noticeDetailService.getDetail(id);
        model.addAttribute("noticeDetail", noticeDetail); // ✅ 키 이름도 noticeDetail
        return "notice/notice-detail"; // templates/notice/notice-detail.html
    }
}
