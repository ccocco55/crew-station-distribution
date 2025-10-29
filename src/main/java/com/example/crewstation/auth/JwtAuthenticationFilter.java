package com.example.crewstation.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.parseTokenFromHeader(request);

        if(token != null) {
            log.info("Token found: {}", token);
            if(jwtTokenProvider.validateToken(token)){
                log.info("Token is valid");
                if(jwtTokenProvider.isTokenBlackList(token)){
                    log.info("Token is blacklisted");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "This token is logout token");
                    return;
                }

                UsernamePasswordAuthenticationToken authentication =
                        (UsernamePasswordAuthenticationToken) jwtTokenProvider.getAuthentication(token);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authentication:"+authentication);
                log.info("Authentication name: {}", authentication.getName());
                log.info("Authentication details: {}", authentication.getAuthorities());
                log.info("Authentication in SecurityContext: {}", SecurityContextHolder.getContext().getAuthentication());
            } else {
                log.warn("Token is not valid");
            }
        } else {
            String cookieRefreshToken = null;
            String provider = null;

            if(request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if("refreshToken".equals(cookie.getName())){
                        cookieRefreshToken = cookie.getValue();
                    }
                    if("provider".equals(cookie.getName())){
                        provider = cookie.getValue();
                    }
                }
            }
            if(cookieRefreshToken != null) {
                String username = jwtTokenProvider.getUserName(cookieRefreshToken);
                String accessToken = null;

                boolean checkRefreshToken = provider != null ? jwtTokenProvider.checkRefreshTokenBetweenCookieAndRedis(username, provider, cookieRefreshToken)
                        : jwtTokenProvider.checkRefreshTokenBetweenCookieAndRedis(username, cookieRefreshToken);

                if (checkRefreshToken) {
                    if (jwtTokenProvider.validateToken(cookieRefreshToken)) {
                        CustomUserDetails customUserDetails = (CustomUserDetails) jwtTokenProvider.getAuthentication(cookieRefreshToken).getPrincipal();
                        if(username.contains("@")){
                            accessToken = jwtTokenProvider.createAccessToken(provider == null ? customUserDetails.getUserEmail() : customUserDetails.getMemberSocialEmail());
                            jwtTokenProvider.createRefreshToken(provider == null ? customUserDetails.getUserEmail() : customUserDetails.getMemberSocialEmail());
                        }else{
                            accessToken = jwtTokenProvider.createAccessToken(customUserDetails.getGuestOrderNumber());
                            jwtTokenProvider.createRefreshToken(customUserDetails.getGuestOrderNumber());
                        }

                        response.setHeader("Authorization", "Bearer " + accessToken);
                        response.sendRedirect(request.getRequestURI());
                        return;
                    }
                }
            }else {
                log.warn("No token found");
            }
        }

//        이 코드를 호출해서 필터 체인에 있는 다음 필터에게 요청과 응답 처리를 넘김
//        만약 doFilter 메소드를 호출하지 않으면, 필터 체인의 다음 필터는 실행되지 않고 요청이 멈춤
        filterChain.doFilter(request, response);
    }
}
