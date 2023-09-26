package com.loveloveshot.user.query.application.service;

import com.loveloveshot.user.command.domain.aggregate.entity.User;
import com.loveloveshot.user.command.domain.repository.UserRepository;
import com.loveloveshot.user.query.application.dto.response.UserQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public UserQueryResponse findUserNoBySocialId(String socialId) {

        User user = userRepository.findBySocialId(socialId).orElseThrow(
                () -> new IllegalArgumentException("일치하는 회원이 없습니다.")
        );

        return new UserQueryResponse(
                user.getUserNo(),
                user.getNickName(),
                user.getSocialType(),
                user.getSocialId(),
                user.getProfileUrl(),
                user.getAge(),
                user.getGender()
        );
    }
}
