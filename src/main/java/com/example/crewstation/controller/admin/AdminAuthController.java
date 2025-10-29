package com.example.crewstation.controller.admin;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.auth.JwtTokenProvider;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.service.member.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController implements AdminAuthControllerDocs {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletResponse response;
    private final MemberService memberService;

    @PostMapping("login")
    @LogReturnStatus
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO, HttpServletRequest request) {
        log.info(memberDTO.toString());
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    memberDTO.getMemberEmail(), memberDTO.getMemberPassword()
                            )
                    );

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
            if (!isAdmin) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "관리자 권한 없음"));
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String username = ((CustomUserDetails) authentication.getPrincipal()).getMemberEmail();
            String accessToken = jwtTokenProvider.createAccessToken(username);
            String refreshToken = jwtTokenProvider.createRefreshToken(username);

            boolean isHttps = request.isSecure();

            Cookie access = new Cookie("accessToken", accessToken);
            access.setHttpOnly(true);
            access.setSecure(isHttps);
            access.setPath("/");

            Cookie refresh = new Cookie("refreshToken", refreshToken);
            refresh.setHttpOnly(true);
            refresh.setSecure(isHttps);
            refresh.setPath("/");

            Cookie roleCookie = new Cookie("role", "ROLE_ADMIN");
            roleCookie.setHttpOnly(false);
            roleCookie.setSecure(isHttps);
            roleCookie.setPath("/");

            if (Boolean.TRUE.equals(memberDTO.getRemember())) {
                Cookie rememberEmailCookie = new Cookie("rememberEmail", memberDTO.getMemberEmail());
                rememberEmailCookie.setHttpOnly(false);
                rememberEmailCookie.setSecure(false);
                rememberEmailCookie.setPath("/");
                rememberEmailCookie.setMaxAge(60 * 60 * 24 * 30);
                response.addCookie(rememberEmailCookie);

            } else {
                Cookie rm = new Cookie("rememberEmail", null);
                rm.setHttpOnly(false);
                rm.setSecure(isHttps);
                rm.setPath("/");
                rm.setMaxAge(0);
                response.addCookie(rm);
            }

            response.addCookie(access);
            response.addCookie(refresh);
            response.addCookie(roleCookie);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);

            return ResponseEntity.ok(tokens);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인 실패: " + e.getMessage()));
        }
    }


    @PostMapping("logout")
    public void logout(@CookieValue(value = "accessToken", required = false) String token,
                       HttpServletRequest request) {

        if (token == null || token.isBlank()) {
            return;
        }

        String username = jwtTokenProvider.getUserName(token);
        String provider = (String) jwtTokenProvider.getClaims(token).get("provider");
        if (provider == null) {
            jwtTokenProvider.deleteRefreshToken(username);
            jwtTokenProvider.addToBlacklist(token);
        } else {
            jwtTokenProvider.deleteRefreshToken(username, provider);
            jwtTokenProvider.addToBlacklist(token);
        }

        boolean isHttps = request.isSecure();

        Cookie deleteAccessCookie = new Cookie("accessToken", null);
        deleteAccessCookie.setHttpOnly(true);
        deleteAccessCookie.setSecure(isHttps);
        deleteAccessCookie.setPath("/");
        deleteAccessCookie.setMaxAge(0);
        response.addCookie(deleteAccessCookie);

        Cookie deleteRefreshCookie = new Cookie("refreshToken", null);
        deleteRefreshCookie.setHttpOnly(true);
        deleteRefreshCookie.setSecure(isHttps);
        deleteRefreshCookie.setPath("/");
        deleteRefreshCookie.setMaxAge(0);
        response.addCookie(deleteRefreshCookie);

        Cookie roleCookie = new Cookie("role", null);
        roleCookie.setHttpOnly(false);
        roleCookie.setSecure(isHttps);
        roleCookie.setPath("/");
        roleCookie.setMaxAge(0);
        response.addCookie(roleCookie);
    }


    @GetMapping("refresh")
    public Map<String, String> refresh(@CookieValue(value = "refreshToken", required = false) String token){
        String username = jwtTokenProvider.getUserName(token);
        String refreshToken = jwtTokenProvider.getRefreshToken(username);
        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("리프레시 토큰이 유효하지 않습니다.");
        }

        CustomUserDetails cud =
                (CustomUserDetails) jwtTokenProvider.getAuthentication(refreshToken).getPrincipal();
        String accessToken = jwtTokenProvider.createAccessToken(cud.getMemberEmail());

        jwtTokenProvider.deleteRefreshToken(username);
        jwtTokenProvider.createRefreshToken(cud.getMemberEmail());

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        return tokenMap;
    }

    @GetMapping("/info")
    public MemberDTO getMyInfo(HttpServletRequest request,
                               @CookieValue(value = "accessToken", required = false) String accessFromCookie) {

        String token = null;
        try {
            token = jwtTokenProvider.parseTokenFromHeader(request);
        } catch (Exception e) {
            log.warn("Failed to parse token from header: {}", e.getMessage());
        }

        if (token == null || token.isBlank()) token = accessFromCookie;
        if (token == null || token.isBlank()) {
            throw new RuntimeException("토큰이 없습니다.");
        }

        if (jwtTokenProvider.isTokenBlackList(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그아웃된 토큰입니다.");
        }

        String memberEmail = jwtTokenProvider.getUserName(token);
        String provider = (String) jwtTokenProvider.getClaims(token).get("provider");

        return memberService.getMember(memberEmail, provider);
    }

}

