package com.rumor.yumback.domains.comments.infrastructure;


import com.rumor.yumback.domains.comments.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReplyJpaRepository extends JpaRepository<Reply, UUID> {
}
