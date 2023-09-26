package com.loveloveshot.user.query.application.service;

import com.loveloveshot.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public void findUserNoBySocialId(String socailId) {
    }
}
