package com.rumor.yumback.domains.users.infrastructure;

import com.rumor.yumback.domains.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);
}
