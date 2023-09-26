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

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("CustomOAuth2UserService.loadUser userRequest = " + userRequest.getClientRegistration());
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
        System.out.println("CustomOAuth2UserService.process userRequest.getClientRegistration = " + userRequest.getClientRegistration());
        SocialType socialType = SocialType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(socialType, user.getAttributes());
        System.out.println("userInfo = " + userInfo);
        User savedUser = userRepository.findBySocialTypeAndSocialId(socialType, userInfo.getSocialId())
                .orElse(null);
        System.out.println("savedUser = " + savedUser);
        if(savedUser == null) {
            savedUser = registUser(userInfo, socialType);
        }
        savedUser = updateUser(savedUser, userInfo);

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    private User registUser(OAuth2UserInfo userInfo, SocialType socialType) {
        System.out.println("CustomOAuth2UserService.registUser userInfo = " + userInfo);
        User user = User.builder()
                .nickName(userInfo.getNickName())
                .socialType(socialType)
                .socialId(userInfo.getSocialId())
                .roleType(RoleType.USER)
                .profileUrl(userInfo.getProfileUrl())
                .age(userInfo.getAge())
                .gender(userInfo.getGender())
                .build();

        return userRepository.saveAndFlush(user);
    }

    private User updateUser(User user, OAuth2UserInfo userInfo) {

//        User findedUser = userRepository.findById(user.getUserNo()).orElse(null);
        if (userInfo.getNickName() != null && !user.getNickName().equals(userInfo.getNickName())) {
            user.updateNickName(userInfo.getNickName());
        }

        if (userInfo.getProfileUrl() != null && !user.getProfileUrl().equals(userInfo.getProfileUrl())) {
            user.updateProfileUrl(userInfo.getProfileUrl());
        }

        if (userInfo.getAge() != null && !user.getAge().equals(userInfo.getAge())) {
            user.updateAge(userInfo.getAge());
        }

        return user;
    }
}
