package com.loveloveshot.oauth.domain.exception;

public class TokenValidFailedException extends RuntimeException {

    public TokenValidFailedException() {
        super("토큰 발행에 실패하였습니다.");
    }

    private TokenValidFailedException(String message) {
        super(message);
    }
}
