package com.example.crewstation.controller.alarm;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
@Slf4j
public class AlarmRestController {

    private final AlarmService alarmService;

    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getUnreadCount(@AuthenticationPrincipal CustomUserDetails user) {
        Map<String, Integer> result = new HashMap<>();

        if (user == null) {
            result.put("count", 0);
            return ResponseEntity.ok(result);
        }

        int count = alarmService.getUnreadCount(user.getId());
        result.put("count", count);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/read")
    public ResponseEntity<Void> markAsRead(@RequestParam String alarmType,
                                           @RequestParam Long alarmId) {
        alarmService.markAsRead(alarmType, alarmId);
        return ResponseEntity.ok().build();
    }
}
