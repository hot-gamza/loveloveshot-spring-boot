package com.loveloveshot.oauth.domain.aggregate.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {

    USER("USER", "회원"),
    ADMIN("ADMIN", "관리자"),
    GUEST("GUEST", "손님");

    private final String code;
    private final String name;

    public static RoleType of(String code) {
        return Arrays.stream(RoleType.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(GUEST);
    }
}
