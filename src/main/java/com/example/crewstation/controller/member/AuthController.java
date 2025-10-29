package com.example.crewstation.controller.member;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.auth.JwtTokenProvider;
import com.example.crewstation.dto.guest.GuestDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.service.member.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/auth/**")
@RequiredArgsConstructor
public class
AuthController implements AuthControllerDocs{
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletResponse response;
    private final MemberService memberService;
    private final RedisTemplate<String, Object> redisTemplate;

    //    로그인
    @PostMapping("login")
    @LogReturnStatus
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO){

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(memberDTO.getMemberEmail(), memberDTO.getMemberPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtTokenProvider.createAccessToken(((CustomUserDetails) authentication.getPrincipal()).getUserEmail());
            String refreshToken = jwtTokenProvider.createRefreshToken(((CustomUserDetails) authentication.getPrincipal()).getUserEmail());

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);

            Cookie rememberEmailCookie = new Cookie("rememberEmail", memberDTO.getMemberEmail());


            rememberEmailCookie.setPath("/"); // 모든 경로에서 접근 가능

            return ResponseEntity.ok(tokens);

        } catch(AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인 실패: " + e.getMessage()));
        }
    }

    //   guest 로그인
    @PostMapping("guest-login")
    @LogReturnStatus
    public ResponseEntity<?> guestLogin(@RequestBody GuestDTO guestDTO){
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(guestDTO.getGuestOrderNumber(), guestDTO.getGuestPhone()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtTokenProvider.createAccessToken(((CustomUserDetails) authentication.getPrincipal()).getGuestOrderNumber());
            String refreshToken = jwtTokenProvider.createRefreshToken(((CustomUserDetails) authentication.getPrincipal()).getGuestOrderNumber());

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);

            Cookie rememberEmailCookie = new Cookie("rememberEmail", guestDTO.getGuestOrderNumber());

            rememberEmailCookie.setPath("/");

            return ResponseEntity.ok(tokens);

        } catch(AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인 실패: " + e.getMessage()));
        }
    }

    //    로그아웃
    @PostMapping("logout")
    public void logout(@CookieValue(value = "accessToken", required = false) String token) {
        log.info(token);
        String username = jwtTokenProvider.getUserName(token);
        String provider = (String) jwtTokenProvider.getClaims(token).get("provider");
        if(provider == null){
            jwtTokenProvider.deleteRefreshToken(username);
            jwtTokenProvider.addToBlacklist(token);
        }else{
            jwtTokenProvider.deleteRefreshToken(username, provider);
            jwtTokenProvider.addToBlacklist(token);
        }

        Cookie deleteAccessCookie = new Cookie("accessToken", null);
        deleteAccessCookie.setHttpOnly(true);
        deleteAccessCookie.setSecure(false);
        deleteAccessCookie.setPath("/");
        deleteAccessCookie.setMaxAge(0);

        response.addCookie(deleteAccessCookie);

        Cookie deleteRefreshCookie = new Cookie("refreshToken", null);
        deleteRefreshCookie.setHttpOnly(true);
        deleteRefreshCookie.setSecure(false);
        deleteRefreshCookie.setPath("/");
        deleteRefreshCookie.setMaxAge(0);

        response.addCookie(deleteRefreshCookie);

        Cookie memberEmailCookie = new Cookie("memberEmail", null);
        memberEmailCookie.setHttpOnly(true);
        memberEmailCookie.setSecure(false);
        memberEmailCookie.setPath("/");
        memberEmailCookie.setMaxAge(0);

        response.addCookie(memberEmailCookie);


        Cookie roleCookie = new Cookie("role", null);
        roleCookie.setHttpOnly(true);
        roleCookie.setSecure(false);
        roleCookie.setPath("/");
        roleCookie.setMaxAge(0);

        response.addCookie(roleCookie);

        Cookie deleteProviderCookie = new Cookie("provider", null);
        deleteProviderCookie.setHttpOnly(true);
        deleteProviderCookie.setSecure(false);
        deleteProviderCookie.setPath("/");
        deleteProviderCookie.setMaxAge(0);

        response.addCookie(deleteProviderCookie);
        memberService.deleteCache("member");
        memberService.deleteCache("diary");
        memberService.deleteCache("country");
        memberService.deleteCache("purchase");

    }

    //    리프레시 토큰으로 엑세스 토큰 발급
    @GetMapping("refresh")
    public Map<String, String> refresh(@CookieValue(value = "refreshToken", required = false) String token){
        String username = jwtTokenProvider.getUserName(token);
        String refreshToken = jwtTokenProvider.getRefreshToken(username);
        if(refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)){
            throw new RuntimeException("리프레시 토큰이 유효하지 않습니다.");
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) jwtTokenProvider.getAuthentication(refreshToken).getPrincipal();
        String accessToken = jwtTokenProvider.createAccessToken(customUserDetails.getUserEmail());

        jwtTokenProvider.deleteRefreshToken(username);
        jwtTokenProvider.createRefreshToken(customUserDetails.getUserEmail());

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);

        return tokenMap;
    }

    @GetMapping("/info")
    public MemberDTO getMyInfo(@CookieValue(name = "accessToken", required = false) String token) {
        if (token == null) {
            throw new RuntimeException("토큰이 없습니다.");
        }

        // 블랙리스트 체크 추가
        if (jwtTokenProvider.isTokenBlackList(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그아웃된 토큰입니다.");
        }

        String memberEmail = jwtTokenProvider.getUserName(token);
        String provider = (String) jwtTokenProvider.getClaims(token).get("provider");
        MemberDTO member = memberService.getMember(memberEmail, provider);

        return member;
    }

    @PostMapping("/reset-cookies")
    public void resetCookies(HttpServletRequest req, HttpServletResponse res){
        Cookie[] cookies = req.getCookies();
        log.info("Cookies are {}", cookies);
        if (cookies != null) {
            boolean accessTokenExists = false;
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    accessTokenExists = true;
                    break;
                }
            }
            if (!accessTokenExists) {
                for (Cookie cookie : cookies) {
                    Cookie newCookie = new Cookie(cookie.getName(), null);
                    newCookie.setHttpOnly(true);
                    newCookie.setSecure(false);
                    newCookie.setPath("/");
                    newCookie.setMaxAge(0);
                    res.addCookie(newCookie);
                }
            }
        }

        Set<String> keys = redisTemplate.keys("refresh:*");
        if(!keys.isEmpty()){
           redisTemplate.delete(keys);
        }
    }

}












