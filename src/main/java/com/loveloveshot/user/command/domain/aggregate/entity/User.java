package com.loveloveshot.user.command.domain.aggregate.entity;

import com.loveloveshot.oauth.domain.aggregate.enumType.RoleType;
import com.loveloveshot.oauth.domain.aggregate.enumType.SocialType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "TBL_USER")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @Column
    private String nickName;

    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SocialType socialType;

    @Column
    private String socialId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    @Column
    private String profileUrl;

    @Builder
    public User(Long userNo, String nickName, String email, SocialType socialType, String socialId, RoleType roleType, String profileUrl) {
        this.userNo = userNo;
        this.nickName = nickName;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.socialType = socialType;
        this.socialId = socialId;
        this.roleType = roleType;
        this.profileUrl = profileUrl != null ? profileUrl : "";
    }

}
