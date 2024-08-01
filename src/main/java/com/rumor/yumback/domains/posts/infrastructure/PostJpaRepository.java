package com.rumor.yumback.domains.posts.infrastructure;


import com.rumor.yumback.domains.posts.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostJpaRepository extends JpaRepository<Post, UUID> {
}
