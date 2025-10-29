package com.example.crewstation.dto.member;

import com.example.crewstation.common.enumeration.DeliveryMethod;
import com.example.crewstation.common.enumeration.PaymentPhase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ModifyDTO {
    private String memberEmail;
    private String memberName;
    private String memberBirth;
    private String memberPhone;
    private String memberAddress;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String memberSocialUrl;
    private String memberProfileImg;

    private String filePath;
    private String fileName;

    // 이미지 URL
    private String profileImageUrl;
}
