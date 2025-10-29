package com.example.crewstation.domain.member;

import com.example.crewstation.audit.Period;
import com.example.crewstation.common.enumeration.Gender;
import com.example.crewstation.common.enumeration.MemberProvider;
import com.example.crewstation.common.enumeration.MemberRole;
import com.example.crewstation.common.enumeration.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@SuperBuilder
public class MemberVO extends Period {
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
    private String socialImgUrl;
    private String memberSocialEmail;
    private String memberDescription;
    private MemberRole memberRole;
}
