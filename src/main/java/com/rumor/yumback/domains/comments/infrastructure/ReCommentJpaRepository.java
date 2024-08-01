package com.rumor.yumback.domains.comments.infrastructure;


import com.rumor.yumback.domains.comments.domain.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReCommentJpaRepository extends JpaRepository<ReComment, UUID> {
}
