package com.loveloveshot.user.command.domain.repository;

import com.loveloveshot.oauth.domain.aggregate.enumType.SocialType;
import com.loveloveshot.user.command.domain.aggregate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
    Optional<User> findByNickName(String nickName);
}
