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

//    private String password;
//    private String email;

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

    @Column
    private String age;

    @Column
    private String gender;

    @Builder
    public User(Long userNo, String nickName, SocialType socialType, String socialId, RoleType roleType, String profileUrl, String age, String gender) {
        this.userNo = userNo;
        this.nickName = nickName;
//        this.password = password;
//        this.email = email;
        this.socialType = socialType;
        this.socialId = socialId;
        this.roleType = roleType;
        this.profileUrl = profileUrl;
        this.age = age;
        this.gender = gender;
    }

    public void updateNickName(String newNickName) {
        this.nickName = newNickName;
    }

    public void updateProfileUrl(String newProfileUrl) {
        this.profileUrl = newProfileUrl;
    }

    public void updateAge(String newAge) {
        this.age = newAge;
    }
}
