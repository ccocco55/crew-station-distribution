package com.example.crewstation.alarm;

import com.example.crewstation.mapper.alarm.AlarmMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class MapperTest {

    @Autowired
    private AlarmMapper alarmMapper;

    @Test
    @Transactional
    public void testInsert() {
        alarmMapper.insertPaymentAlarm(2L);
    }



    @Test
    @Transactional
    public void testInsertLike() {
        alarmMapper.insertLikeAlarm(2L);
    }

    @Test
    @Transactional
    public void testInsertReplyAlarm() {
        alarmMapper.insertReplyAlarm(53L);
    }

}
