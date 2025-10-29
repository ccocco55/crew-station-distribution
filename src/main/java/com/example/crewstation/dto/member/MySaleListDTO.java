package com.example.crewstation.dto.member;

import com.example.crewstation.common.enumeration.PaymentPhase;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MySaleListDTO {
    private Long memberId;
    private Long postId;
    private String postTitle;
    private String purchaseProductPrice;
    private Long fileId;
    private String filePath;
    private String fileName;
    private String fileOriginName;
    private String createdDatetime;
    private String updatedDatetime;
    private PaymentPhase status;
    private Long paymentStatusId;

    public String getFormattedCreatedDatetime() {
        return formatDateString(createdDatetime);
    }

    public String getFormattedUpdatedDatetime() {
        return formatDateString(updatedDatetime);
    }

    private String formatDateString(String datetime) {
        if (datetime == null || datetime.isBlank()) return "";
        try {
            DateTimeFormatter dbFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
            LocalDateTime parsed = LocalDateTime.parse(datetime, dbFormat);
            return parsed.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        } catch (DateTimeParseException e1) {
            try {
                DateTimeFormatter dbFormatFallback = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime parsed = LocalDateTime.parse(datetime, dbFormatFallback);
                return parsed.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            } catch (Exception e2) {
                return datetime; // 포맷 실패 시 원본 반환
            }
        }
    }
}
