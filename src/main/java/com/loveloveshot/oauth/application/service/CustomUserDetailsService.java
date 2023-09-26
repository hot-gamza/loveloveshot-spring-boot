package com.loveloveshot.oauth.application.service;

import com.loveloveshot.oauth.domain.aggregate.entity.UserPrincipal;
import com.loveloveshot.user.command.domain.aggregate.entity.User;
import com.loveloveshot.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
        User user = userRepository.findByNickName(nickName)
                .orElseThrow(
                        () -> new UsernameNotFoundException("일치하는 이름이 없습니다.")
                );
        return UserPrincipal.create(user);
    }
}
