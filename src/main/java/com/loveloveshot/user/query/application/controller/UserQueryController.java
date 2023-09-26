package com.loveloveshot.user.query.application.controller;

import com.loveloveshot.common.response.ApiResponse;
import com.loveloveshot.oauth.token.AuthToken;
import com.loveloveshot.oauth.token.AuthTokenProvider;
import com.loveloveshot.user.query.application.service.UserQueryService;
import com.loveloveshot.utils.HeaderUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserQueryController {

    private AuthTokenProvider authTokenProvider;
    private UserQueryService userQueryService;

    @GetMapping("/findUser")
    public ResponseEntity<ApiResponse> findUser (HttpServletRequest request) {

        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(accessToken);
        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse.error("토큰이 만료되었습니다. 다시 로그인해주세요."));
        }
        String socailId = authToken.getSocialIdFromToken();
        userQueryService.findUserNoBySocialId(socailId);
        return ResponseEntity
    }

}
