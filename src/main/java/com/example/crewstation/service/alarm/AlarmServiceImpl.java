package com.example.crewstation.service.alarm;

import com.example.crewstation.dto.alarm.AlarmDTO;
import com.example.crewstation.repository.alarm.AlarmDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmServiceImpl implements AlarmService {
    private final AlarmDAO alarmDAO;

    @Override
    public int getUnreadCount(Long memberId) {
        return alarmDAO.selectUnreadCount(memberId);
    }

    @Override
    public List<AlarmDTO> getLatestAlarms(Long memberId) {
        return alarmDAO.selectLatestAlarms(memberId);
    }

    @Override
    @Transactional
    public void markAsRead(String alarmType, Long alarmId) {
        alarmDAO.markAlarmAsRead(alarmType, alarmId);

    }
}
