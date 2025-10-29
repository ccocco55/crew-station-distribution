package com.example.crewstation.interceptor;

import com.example.crewstation.auth.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = null;
        String accessToken = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                    break;
                }
            }

        };

        if(accessToken != null){
            username = jwtTokenProvider.getUserName(accessToken);
        }

        if (username != null) {
            if(username.contains("@")){

                String ua = request.getHeader("User-Agent");

                // 모바일 여부 판단
                boolean isMobile = ua != null && (ua.contains("iPhone") || ua.contains("Android"));

                if (isMobile) {
                    response.sendRedirect("/gifts");
                } else {
                    response.sendRedirect("/");
                }
                return false;
            }else{
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

                jwtTokenProvider.deleteRefreshToken(username);
            }
        };
        return true;
    }

}
