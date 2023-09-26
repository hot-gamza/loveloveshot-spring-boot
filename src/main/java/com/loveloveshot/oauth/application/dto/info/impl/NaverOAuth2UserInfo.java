package com.loveloveshot.oauth.application.dto.info.impl;

import com.loveloveshot.oauth.application.dto.info.OAuth2UserInfo;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getSocialId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("id");
    }

    @Override
    public String getNickName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        System.out.println("네이버 닉네임 response = " + response);
//        if (response == null) {
//            return null;
//        }

        return (String) response.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("email");
    }

    @Override
    public String getProfileUrl() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

//        if (response == null) {
//            return null;
//        }

        return (String) response.get("profile_image");
    }

    @Override
    public String getAge() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }
        return (String) response.get("age");
    }

    @Override
    public String getGender() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

//        if (response == null) {
//            return null;
//        }
        return (String) response.get("gender");
    }
}
