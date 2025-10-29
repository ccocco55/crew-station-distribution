package com.example.crewstation.mapper.alarm;

import com.example.crewstation.dto.alarm.AlarmDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface AlarmMapper {

//    결제 알람 보내기
    public void insertPaymentAlarm(Long paymentStatusId);

//    좋아요 알람 보내기
    public void insertLikeAlarm(Long likeId);

//   좋아요 알람 삭제
    public void deleteLikeAlarm(Long likeId);

//   댓글 알람 추가
    public void insertReplyAlarm(Long postId);

//    멤버 알람 추가
    public void insertMemberAlarm(@Param("memberId") Long memberId);

//    안 읽은 알람 개수 조회
    public int selectUnreadCount(@Param("memberId") Long memberId);

//    알림페이지 목록
    public List<AlarmDTO> selectLatestAlarms (@Param("memberId") Long memberId);

//    알림 읽음 처리
    public int markAlarmAsRead(@Param("alarmType") String alarmType,
                        @Param("alarmId") Long alarmId);
}

