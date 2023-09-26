package com.loveloveshot.oauth.domain.aggregate.entity;

import com.loveloveshot.oauth.domain.aggregate.enumType.RoleType;
import com.loveloveshot.oauth.domain.aggregate.enumType.SocialType;
import com.loveloveshot.user.command.domain.aggregate.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * 사용자 정보 제공 및 저장 :
 * Spring Security 및 OAuth2 인증 과정 중 사용자의 기본 정보와 권한을 저장합니다.
 * 이 정보는 Spring Security에서 사용자를 식별하고 권한 부여에 필요합니다.
 */
@Getter
@RequiredArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails, OidcUser {

    private final String socialId;
    private final SocialType socialType;
    private final String name;
    private final RoleType roleType;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Builder
    public UserPrincipal(String socialId, SocialType socialType, String name, RoleType roleType, Collection<GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.socialId = socialId;
        this.socialType = socialType;
        this.name = name;
        this.roleType = roleType;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static UserPrincipal create(User user) {
        return UserPrincipal.builder()
                .socialId(user.getSocialId())
                .socialType(user.getSocialType())
                .name(user.getNickName())
                .roleType(user.getRoleType())
                .authorities(
                        Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode()))
                ).build();
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        return UserPrincipal.builder()
                .socialId(user.getSocialId())
                .socialType(user.getSocialType())
                .name(user.getNickName())
                .roleType(user.getRoleType())
                .authorities(
                        Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode()))
                )
                .attributes(attributes)
                .build();
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return socialId;
    }

    @Override
    public String getUsername() {
        return socialId;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }
}