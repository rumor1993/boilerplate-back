package com.rumor.yumback.domains.posts.infrastructure;


import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.posts.domain.PostLike;
import com.rumor.yumback.domains.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostLikeJpaRepository extends JpaRepository<PostLike, UUID> {
    Optional<PostLike> findByPostAndUser(Post foundPost, User foundUser);

    List<PostLike> findAllByPost(Post foundPost);
}
