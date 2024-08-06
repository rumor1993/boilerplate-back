package com.rumor.yumback.domains.comments.infrastructure;


import aj.org.objectweb.asm.commons.Remapper;
import com.rumor.yumback.domains.comments.domain.CommentLikes;
import com.rumor.yumback.domains.comments.domain.ReComment;
import com.rumor.yumback.domains.comments.domain.ReCommentLikes;
import com.rumor.yumback.domains.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReCommentLikeJpaRepository extends JpaRepository<ReCommentLikes, UUID> {
    Optional<ReCommentLikes> findByReCommentAndUser(ReComment foundReComment, User foundUser);
}
