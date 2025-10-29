package com.example.crewstation.controller.admin;

import com.example.crewstation.dto.member.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth", description = "Auth API")
public interface AdminAuthControllerDocs {
    @Operation(
            summary = "회원 로그인",
            description = "일반 회원이 로그인 할 때 토큰 발급",
            parameters = {
                    @Parameter(name = "memberDTO", description = "로그인 화면에서 입력한 인증 정보")
            }
    )
    default ResponseEntity<?> login(@RequestBody MemberDTO memberDTO) {
        return null;
    }

    @Operation(
            summary = "로그아웃",
            description = "일반 회원과 SNS 회원의 로그인 정보를 모두 제거하고 로그아웃 처리한다.",
            parameters = {
                    @Parameter(name = "token", description = "쿠키에 있는 access token이 들어온다.")
            }
    )
    default void logout(@CookieValue(value = "accessToken", required = false) String token) {
    }
}



