package com.rumor.yumback.domains.users.application;

import com.rumor.yumback.common.errors.UserNotFoundException;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.infrastructure.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserJpaRepository userJpaRepository;

    public User profile(String username) {
        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Not Found User"));
    }

    public User edit(UUID id, UserEditorDto dto) {
        User foundUser = userJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Not Found User"));

        boolean empty = userJpaRepository.findByName(dto.name()).isEmpty();

        if (!empty) {
            throw new RuntimeException("User already exists");
        }

        foundUser.changeName(dto.name());
        return foundUser;
    }
}
