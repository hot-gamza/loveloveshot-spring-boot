package com.loveloveshot.oauth.application.controller;

import com.loveloveshot.common.response.ApiResponse;
import com.loveloveshot.configuration.properties.AppProperties;
import com.loveloveshot.oauth.domain.aggregate.entity.UserPrincipal;
import com.loveloveshot.oauth.domain.aggregate.enumType.RoleType;
import com.loveloveshot.oauth.token.AuthToken;
import com.loveloveshot.oauth.token.AuthTokenProvider;
import com.loveloveshot.oauth.application.dto.request.AuthCommandRequest;
import com.loveloveshot.utils.HeaderUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

//    private final static long THREE_DAYS_MSEC = 259200000;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody AuthCommandRequest authRequest) {
        System.out.println("authRequest = " + authRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getId(),
                        authRequest.getPassword()
                )
        );

        String userId = authRequest.getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userId,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        return ApiResponse.success("token", accessToken.getToken());
    }

    @GetMapping("/reIssue")
    public ResponseEntity<ApiResponse> reIssueAccessToken (HttpServletRequest request) {

        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if (!authToken.validate()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse.error("로그인 유효 시간이 만료되었습니다."));
        }

        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse.error("토큰이 만료되었습니다."));
        }

        String socialId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                socialId,
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        return ResponseEntity.ok(ApiResponse.success("token", newAccessToken.getToken()));
    }
}
