package com.loveloveshot.oauth.domain.aggregate.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
    GOOGEL("google"),
    KAKAO("kakao"),
    NAVER("naver");

    private final String name;
}
