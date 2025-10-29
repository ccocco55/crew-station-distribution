package com.example.crewstation.common;

import com.example.crewstation.auth.JwtTokenProvider;
import com.example.crewstation.interceptor.GuestInterceptor;
import com.example.crewstation.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GuestInterceptor(jwtTokenProvider))
                .excludePathPatterns(
                        "/",
                        "/api/alarms/**",
                        "/api/guest/order-detail/**",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/fonts/**",
                        "/guest/order-detail",
                        "/mobile/login",
                        "/member/login",
                        "/error",
                        "/.well-known/**",
                        "/diaries/",
                        "/api/auth/guest-login",
                        "/admin/login",
                        "/api/payment",
                        "/error",
                        "/api/auth/**",
                        "/api/admin/auth/**",
                        "/member/join",
                        "/member/login",
                        "/member/sns/join",
                        "/mobile/join",
                        "/mobile/login",
                        "/mobile/sns/join",
                        "/member/forgot-password",
                        "/api/member/**",
                        "/member/reset-password-success",
                        "/mobile/reset-password-success",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/diaries/detail/**",
                        "/member/profile/**",
                        "/api/diaries",
                        "/api/replies/{postId:\\d+}",
                        "/gifts",
                        "/gifts/detail/{postId:\\d+}",
                        "/api/gifts",
                        "/company",
                        "/search",
                        "/ask/register",
                        "/notice/notice",
                        "/notice/notice-detail",
                        "/api/guest/order-detail/{guestOrderNumber:\\d+}",
                        "/api/guest/order/status/**",
                        "/api/payment/complete");

        registry.addInterceptor(new LoginInterceptor(jwtTokenProvider))
                .addPathPatterns("/member/login",
                        "/mobile/login");
    }


}
