package com.loveloveshot.user.command.application.service;

import com.loveloveshot.oauth.domain.aggregate.enumType.SocialType;
import com.loveloveshot.user.command.domain.aggregate.entity.User;
import com.loveloveshot.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;

    public User getUser(SocialType socialType, String socialId) {
        return userRepository.findBySocialTypeAndSocialId(socialType, socialId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );
    }
}
