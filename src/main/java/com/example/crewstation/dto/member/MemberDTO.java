package com.example.crewstation.dto.member;

import com.example.crewstation.common.enumeration.Gender;
import com.example.crewstation.common.enumeration.MemberProvider;
import com.example.crewstation.common.enumeration.MemberRole;
import com.example.crewstation.common.enumeration.Status;
import com.example.crewstation.dto.file.FileDTO;
import com.example.crewstation.dto.file.member.MemberFileDTO;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String memberName;
    private String memberPhone;
    private String memberEmail;
    private String memberSocialUrl;
    private String memberBirth;
    private Gender memberGender;
    private String memberMbti;
    private String memberPassword;
    private Status memberStatus;
    private MemberProvider memberProvider;
    private String guestOrderNumber;
    private String socialImgUrl;
    private String memberSocialEmail;
    private String memberDescription;
    private MemberRole memberRole;
    private String createdDatetime;
    private String updatedDatetime;
    private AddressDTO addressDTO;
    private MemberFileDTO memberFileDTO;
    private FileDTO fileDTO;
    private String filePath;
    private Boolean remember;
    public MemberRole getMemberRole() {
        return memberRole != null ? memberRole : MemberRole.MEMBER;
    }
    private Long chemistryScore;
    private int diaryCount;
}
