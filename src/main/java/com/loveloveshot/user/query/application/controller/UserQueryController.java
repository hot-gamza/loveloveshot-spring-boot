package com.loveloveshot.user.query.application.controller;

import com.loveloveshot.common.response.ApiResponse;
import com.loveloveshot.oauth.token.AuthToken;
import com.loveloveshot.oauth.token.AuthTokenProvider;
import com.loveloveshot.user.query.application.dto.response.UserQueryResponse;
import com.loveloveshot.user.query.application.service.UserQueryService;
import com.loveloveshot.utils.HeaderUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.security.PrivateKey;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "http://192.168.0.25:3000")
public class UserQueryController {

    private AuthTokenProvider authTokenProvider;
    private AuthenticationManager authenticationManager;
    private UserQueryService userQueryService;

    @GetMapping("/findUser")
    public ResponseEntity<ApiResponse> findUser (HttpServletRequest request) {


        String accessToken = HeaderUtil.getAccessToken(request);
        String jwtKey = "926D96C90030DD58429D2751AC1BDBBC";
        AuthToken authToken = authTokenProvider.convertAuthToken1(accessToken, Keys.hmacShaKeyFor(jwtKey.getBytes())) ;
        if (!authToken.validate()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse.error("로그인 유효 시간이 만료되었습니다."));
        }

        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse.error("토큰이 만료되었습니다."));
        }

        String socialId = claims.getSubject();
        System.out.println("socialId = " + socialId);
//        String accessToken = HeaderUtil.getAccessToken(request);
//        System.out.println("accessToken = " + accessToken);
//        AuthToken authToken = authTokenProvider.convertAuthToken(accessToken);
//        if (!authToken.validate()) {
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse.error("로그인 유효 시간이 만료되었습니다."));
//        }
//        System.out.println("authToken = " + authToken);
//        Claims claims = authToken.getExpiredTokenClaims();
//        if (claims == null) {
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse.error("토큰이 만료되었습니다. 다시 로그인해주세요."));
//        }
//        String socailId = authToken.getSocialIdFromToken();
//        UserQueryResponse response = userQueryService.findUserNoBySocialId(socailId);

        return ResponseEntity.ok(ApiResponse.success("성공적으로 조회되었습니다.", socialId));
    }

}
