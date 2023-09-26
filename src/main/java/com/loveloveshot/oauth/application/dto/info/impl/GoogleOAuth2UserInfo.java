package com.loveloveshot.oauth.application.dto.info.impl;

import com.loveloveshot.oauth.application.dto.info.OAuth2UserInfo;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getSocialId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getNickName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getProfileUrl() {
        return (String) attributes.get("picture");
    }

    @Override
    public String getAge() {
        return null ;
    }

    @Override
    public String getGender() {
        return null;
    }
}
