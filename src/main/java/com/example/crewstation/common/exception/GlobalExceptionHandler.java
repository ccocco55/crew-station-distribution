package com.example.crewstation.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({GuestNotFoundException.class})
    public RedirectView handleGuestNotFoundException(
            GuestNotFoundException e,
            HttpServletRequest request
    ) {
        String ua = request.getHeader("User-Agent");

        // 모바일 여부 판단
        boolean isMobile = ua != null && (ua.contains("iPhone") || ua.contains("Android"));

        if (isMobile) {
            return new RedirectView("/mobile/login");
        } else {
            return new RedirectView("/member/login");
        }
    }

    @ExceptionHandler(PurchaseNotFoundException.class)
    public RedirectView handlePurchaseNotFoundException(PurchaseNotFoundException e) {
        return new RedirectView("/gifts");
    }
    @ExceptionHandler(DiaryNotFoundException.class)
    public RedirectView handleDiaryNotFoundException(DiaryNotFoundException e) {
        return new RedirectView("/diaries");
    }

    @ExceptionHandler({MemberNotFoundException.class})
    public RedirectView handleMemberNotFoundException(MemberNotFoundException e){
        return new RedirectView("/member/login");
    }

    @ExceptionHandler(MemberLoginFailException.class)
    public RedirectView handleMemberLoginFailException(
            MemberLoginFailException e,
            HttpServletRequest request
    ) {
        String ua = request.getHeader("User-Agent");

        boolean isMobile = ua != null && (ua.contains("iPhone") || ua.contains("Android"));

        if (isMobile) {
            return new RedirectView("/mobile/login");
        } else {
            return new RedirectView("/member/login");
        }
    }
}
