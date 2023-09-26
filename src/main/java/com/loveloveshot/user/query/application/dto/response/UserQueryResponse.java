package com.loveloveshot.user.query.application.dto.response;

import com.loveloveshot.oauth.domain.aggregate.enumType.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryResponse {

    private Long userNo;
    private String nickName;
    private SocialType socialType;
    private String socialId;
    private String profileUrl;
    private String age;
    private String gender;
}
