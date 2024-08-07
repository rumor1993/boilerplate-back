package com.rumor.yumback.domains.comments.infrastructure;


import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.comments.domain.CommentLike;
import com.rumor.yumback.domains.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLike, UUID> {
    Optional<CommentLike> findByCommentAndUser(Comment foundComment, User foundUser);
}
