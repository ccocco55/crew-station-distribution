package com.example.crewstation.auth;

import com.example.crewstation.common.enumeration.Gender;
import com.example.crewstation.common.enumeration.MemberProvider;
import com.example.crewstation.common.enumeration.MemberRole;
import com.example.crewstation.common.enumeration.Status;
import com.example.crewstation.dto.guest.GuestDTO;
import com.example.crewstation.dto.member.MemberDTO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {
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

    public CustomUserDetails(MemberDTO memberDTO) {
        this.id = memberDTO.getId();
        this.memberName = memberDTO.getMemberName();
        this.memberPhone = memberDTO.getMemberPhone();
        this.memberEmail = memberDTO.getMemberEmail();
        this.memberSocialUrl = memberDTO.getMemberSocialUrl();
        this.memberBirth = memberDTO.getMemberBirth();
        this.memberGender = memberDTO.getMemberGender();
        this.memberMbti = memberDTO.getMemberMbti();
        this.memberPassword = memberDTO.getMemberPassword();
        this.memberStatus = memberDTO.getMemberStatus();
        this.memberProvider = memberDTO.getMemberProvider();
        this.guestOrderNumber = memberDTO.getGuestOrderNumber();
        this.socialImgUrl = memberDTO.getSocialImgUrl();
        this.memberSocialEmail = memberDTO.getMemberSocialEmail();
        this.memberDescription = memberDTO.getMemberDescription();
        this.memberRole = memberDTO.getMemberRole();
        this.createdDatetime = memberDTO.getCreatedDatetime();
        this.updatedDatetime = memberDTO.getUpdatedDatetime();
    }

    public CustomUserDetails(GuestDTO guestDTO) {
        this.id = guestDTO.getId();
        this.guestOrderNumber = guestDTO.getGuestOrderNumber();
        this.memberPhone = guestDTO.getGuestPhone();
        this.memberPassword = guestDTO.getGuestPassword();
        this.memberRole = MemberRole.MEMBER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return memberRole.getAuthorities();
    }

    @Override
    public String getPassword() {
        return memberPassword;
    }

    @Override
    public String getUsername() {
        return guestOrderNumber != null ? guestOrderNumber : memberName;
    }

    public String getUserEmail () {return this.memberEmail;}
}
