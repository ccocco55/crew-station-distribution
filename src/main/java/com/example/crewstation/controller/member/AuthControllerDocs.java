package com.example.crewstation.controller.member;

import com.example.crewstation.dto.member.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

// http://localhost:10000/swagger-ui/index.html
@Tag(name = "Auth", description = "Auth API")
public interface AuthControllerDocs {
    @Operation(summary = "회원 로그인",
            description = "일반 회원이 로그인 할 때 토큰 발급",
            parameters = {
                @Parameter(name = "memberDTO", description = "로그인 화면에서 입력한 인증 정보")
            }
    )
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO);

    @Operation(summary = "로그아웃",
            description = "일반 회원과 SNS 회원의 로그인 정보를 모두 제거하고 로그아웃 처리한다.",
            parameters = {
                    @Parameter(name = "token", description = "쿠키에 있는 access token이 들어온다.")
            }
    )
    public void logout(@CookieValue(value = "accessToken", required = false) String token);

    @Operation(summary = "쿠키 전체 삭제 및 redis refresh 삭제",
            description = "로그인 페이지로 넘어왔을 때, 남아있는 쿠키와 refresh를 삭제해준다"
    )
    public void resetCookies(HttpServletRequest req, HttpServletResponse res);

}
