package com.rumor.yumback.domains.posts.infrastructure;


import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostJpaRepository extends JpaRepository<Post, UUID> {
    Optional<Post> findByIdAndCreator(UUID id, User creator);
}
