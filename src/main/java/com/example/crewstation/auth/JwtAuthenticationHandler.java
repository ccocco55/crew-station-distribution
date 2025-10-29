package com.example.crewstation.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("AuthenticationEntryPoint Exception: {}", authException.getMessage());
        if(request.getRequestURI().startsWith("/api/")){
//            REST 요청인 경우
            log.error("AuthenticationEntryPoint Exception: {}", authException.getMessage());
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("로그인 후 사용 가능");
            response.getWriter().flush();
        }else{
            if (request.getRequestURI().startsWith("/mobile/")) {
                response.sendRedirect("/mobile/login");
            } else {

//            일반 웹 요청인 경우
                response.sendRedirect("/member/login");
            }
        }
    }
}
