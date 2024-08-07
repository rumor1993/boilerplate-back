package com.rumor.yumback.domains.comments.infrastructure;


import com.rumor.yumback.domains.comments.domain.ReplyLike;
import com.rumor.yumback.domains.comments.domain.Reply;
import com.rumor.yumback.domains.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReplyLikeJpaRepository extends JpaRepository<ReplyLike, UUID> {
    Optional<ReplyLike> findByReplyAndUser(Reply foundReComment, User foundUser);
}
