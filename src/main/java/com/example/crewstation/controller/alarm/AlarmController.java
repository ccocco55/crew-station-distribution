package com.example.crewstation.controller.alarm;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.alarm.AlarmDTO;
import com.example.crewstation.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/alarm")
@RequiredArgsConstructor
@Slf4j
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public String alarmPage(@AuthenticationPrincipal CustomUserDetails user,
                            Model model) {
        if (user == null) {
            model.addAttribute("alarms", List.of());
            return "main-page/alarm";
        }

        List<AlarmDTO> alarms = alarmService.getLatestAlarms(user.getId());
        model.addAttribute("alarms", alarms);

        return "main-page/alarm";
    }
}
