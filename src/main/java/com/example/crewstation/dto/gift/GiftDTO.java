package com.example.crewstation.dto.gift;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
public class GiftDTO {
    private Long postId;
    private String limitDateTime;
    private String postTitle;
    private String purchaseCountry;
    private String purchaseDeliveryMethod;
    private Integer purchaseLimitTime;
    private Integer purchaseProductCount;
    private Integer purchaseProductPrice;
    private String createdDatetime;
    private String updatedDatetime;
    private String filePath;
    private String fileName;
    private String memberName;
    private String memberFilePath;
    private Integer chemistryScore;
    private String socialImgUrl;
    private String relativeDate;
}
