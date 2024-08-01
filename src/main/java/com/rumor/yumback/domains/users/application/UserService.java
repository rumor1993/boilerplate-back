package com.rumor.yumback.domains.users.application;

import com.rumor.yumback.common.errors.UserNotFoundException;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.infrastructure.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;

    public User profile(String username) {
        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Not Found User"));
    }
}
