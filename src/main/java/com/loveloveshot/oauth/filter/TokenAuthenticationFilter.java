package com.loveloveshot.oauth.filter;

import com.loveloveshot.oauth.token.AuthToken;
import com.loveloveshot.oauth.token.AuthTokenProvider;
import com.loveloveshot.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {
        System.out.println("===================================12!@#!$@#!@");
        System.out.println("토큰 받아오기 전");
        String tokenStr = HeaderUtil.getAccessToken(request);
        System.out.println("받아온 토큰 tokenStr = " + tokenStr);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);

        if (token.validate()) {

            System.out.println("토큰 유효성 검사");
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
