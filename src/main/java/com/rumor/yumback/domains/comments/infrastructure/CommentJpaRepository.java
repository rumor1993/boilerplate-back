package com.rumor.yumback.domains.comments.infrastructure;


import com.rumor.yumback.domains.comments.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentJpaRepository extends JpaRepository<Comment, UUID> {
}
