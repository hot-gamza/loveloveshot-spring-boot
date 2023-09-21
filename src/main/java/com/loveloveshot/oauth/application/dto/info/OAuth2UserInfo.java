package com.loveloveshot.oauth.application.dto.info;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getSocialId();

    public abstract String getNickName();

    public abstract String getEmail();

    public abstract String getProfileUrl();

    public abstract String getAge();

    public abstract String getGender();
}
