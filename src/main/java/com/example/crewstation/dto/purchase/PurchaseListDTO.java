package com.example.crewstation.dto.purchase;

import com.example.crewstation.common.enumeration.PaymentPhase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "postId")
public class PurchaseListDTO {
    private Long memberId;
    private Long postId;
    private String postTitle;
    private String purchaseProductPrice;
    private int price;
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

    private String formatDateString(String datetime) {
        if (datetime == null || datetime.isBlank()) return "";
        try {
            DateTimeFormatter dbFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
            LocalDateTime parsed = LocalDateTime.parse(datetime, dbFormat);
            DateTimeFormatter viewFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            return parsed.format(viewFormat);
        } catch (DateTimeParseException e1) {
            try {
                DateTimeFormatter dbFormatFallback = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime parsed = LocalDateTime.parse(datetime, dbFormatFallback);
                DateTimeFormatter viewFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                return parsed.format(viewFormat);
            } catch (Exception e2) {
                return datetime;
            }
        }
    }
}
