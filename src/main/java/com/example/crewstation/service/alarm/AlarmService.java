package com.example.crewstation.service.alarm;


import com.example.crewstation.dto.alarm.AlarmDTO;

import java.util.List;

public interface AlarmService {
//    안읽은 알람 개수 조회
    public int getUnreadCount(Long memberId);

//    알림 목록 조회
    public List<AlarmDTO> getLatestAlarms(Long memberId);

//    알림 읽음 처리
    public void markAsRead(String alarmType, Long alarmId);

}
