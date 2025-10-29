package com.example.crewstation.repository.alarm;

import com.example.crewstation.dto.alarm.AlarmDTO;
import com.example.crewstation.mapper.alarm.AlarmMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AlarmDAO {

    private final AlarmMapper alarmMapper;

    //    결제 알람 보내기
    public void savePaymentAlarm(Long paymentStatusId) {
        alarmMapper.insertPaymentAlarm(paymentStatusId);
    }

    //    좋아요 알람 보내기
    public void saveLikeAlarm(Long likeId) {
        alarmMapper.insertLikeAlarm(likeId);
    }

    //   좋아요 알람 삭제
    public void deleteLikeAlarm(Long likeId){
        alarmMapper.deleteLikeAlarm(likeId);
    }

    //   댓글 알람 추가
    public void saveReplyAlarm(Long postId){
        alarmMapper.insertReplyAlarm(postId);
    }

//    멤버 알람 추가
    public void saveMemberAlarm(Long memberId) {
        alarmMapper.insertMemberAlarm(memberId);
    }

//    안읽은 알람 갯수
     public int selectUnreadCount(Long memberId){
        return alarmMapper.selectUnreadCount(memberId);
     }

//     알림 목록
    public List<AlarmDTO> selectLatestAlarms(Long memberId) {
        return alarmMapper.selectLatestAlarms(memberId);
    }

//    읽음 처리
    public int markAlarmAsRead(String alarmType, Long alarmId) {
        return alarmMapper.markAlarmAsRead(alarmType, alarmId);
    }
}
