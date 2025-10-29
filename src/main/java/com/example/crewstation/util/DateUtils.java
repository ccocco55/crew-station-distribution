package com.example.crewstation.util;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateUtils {
    public static String toRelativeTime(String date) {
        if (date == null || date.isEmpty()) {
            return "";
        }

        try {
            // 마이크로초 제거 (있어도 없어도 안전)
            String splitDate = date.split("\\.")[0];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime pastDateTime = LocalDateTime.parse(splitDate, formatter);
            LocalDateTime now = LocalDateTime.now();

            Duration duration = Duration.between(pastDateTime, now);
            long seconds = duration.getSeconds();

            if (seconds < 60) {
                return "방금 전";
            }

            long minutes = seconds / 60;
            if (minutes < 60) {
                return minutes + "분 전";
            }

            long hours = minutes / 60;
            if (hours < 24) {
                return hours + "시간 전";
            }

            long days = hours / 24;
            if (days < 30) {
                return days + "일 전";
            }

            long months = days / 30;
            if (months < 12) {
                return months + "개월 전";
            }

            long years = months / 12;
            return years + "년 전";

        } catch (Exception e) {
            return date;
        }
    }


    public static String calcLimitDateTime(String date,int limit){
        // 입력 포맷 (마이크로초 6자리 처리)
        String splitDate = date.split("\\.")[0];
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dt = LocalDateTime.parse(splitDate, inputFormatter);

        // 24시간 더하기
        LocalDateTime dtAdded = dt.plusHours(limit);

        // 출력 포맷 (ISO8601 스타일)
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return dtAdded.format(outputFormatter);
    }
}
