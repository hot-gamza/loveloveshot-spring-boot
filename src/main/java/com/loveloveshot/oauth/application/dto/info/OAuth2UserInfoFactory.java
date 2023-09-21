package com.loveloveshot.oauth.application.dto.info;

import com.loveloveshot.oauth.application.dto.info.impl.GoogleOAuth2UserInfo;
import com.loveloveshot.oauth.application.dto.info.impl.KakaoOAuth2UserInfo;
import com.loveloveshot.oauth.application.dto.info.impl.NaverOAuth2UserInfo;
import com.loveloveshot.oauth.domain.aggregate.enumType.SocialType;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(SocialType socialType, Map<String, Object> attributes) {
        switch (socialType) {
            case GOOGEL: return new GoogleOAuth2UserInfo(attributes);
            case NAVER: return new NaverOAuth2UserInfo(attributes);
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("유효하지 않는 소셜 타입입니다.");
        }
    }
}
