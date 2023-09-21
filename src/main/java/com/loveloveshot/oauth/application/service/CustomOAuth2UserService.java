package com.loveloveshot.oauth.application.service;

import com.loveloveshot.oauth.application.dto.info.OAuth2UserInfo;
import com.loveloveshot.oauth.application.dto.info.OAuth2UserInfoFactory;
import com.loveloveshot.oauth.domain.aggregate.entity.UserPrincipal;
import com.loveloveshot.oauth.domain.aggregate.enumType.RoleType;
import com.loveloveshot.oauth.domain.aggregate.enumType.SocialType;
import com.loveloveshot.user.command.domain.aggregate.entity.User;
import com.loveloveshot.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        SocialType socialType = SocialType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(socialType, user.getAttributes());
        User savedUser = userRepository.findBySocialTypeAndSocialId(socialType, userInfo.getSocialId())
                .orElse(null);

        if(savedUser == null) {
            savedUser = registUser(userInfo, socialType);
        }

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    private User registUser(OAuth2UserInfo userInfo, SocialType socialType) {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .nickName(userInfo.getNickName())
                .socialType(socialType)
                .email(userInfo.getEmail())
                .socialId(userInfo.getSocialId())
                .roleType(RoleType.USER)
                .profileUrl(userInfo.getProfileUrl())
                .build();

        return userRepository.saveAndFlush(user);
    }

//    private User updateUser(User user, OAuth2UserInfo userInfo) {
//
//        User userRepository.findById(user.getUserNo())
//        if (userInfo.getName() != null && !user.getUsername().equals(userInfo.getName())) {
//            user.setUsername(userInfo.getName());
//        }
//
//        if (userInfo.getImageUrl() != null && !user.getProfileImageUrl().equals(userInfo.getImageUrl())) {
//            user.setProfileImageUrl(userInfo.getImageUrl());
//        }
//
//        return user;
//    }
}
