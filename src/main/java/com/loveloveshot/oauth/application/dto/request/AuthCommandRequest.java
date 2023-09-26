package com.loveloveshot.oauth.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthCommandRequest {

    private String id;
    private String password;

    @Builder
    public AuthCommandRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
