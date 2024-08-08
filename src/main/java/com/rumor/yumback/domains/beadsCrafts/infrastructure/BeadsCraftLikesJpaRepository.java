package com.rumor.yumback.domains.beadsCrafts.infrastructure;

import com.rumor.yumback.domains.beadsCrafts.domain.BeadsCraft;
import com.rumor.yumback.domains.beadsCrafts.domain.BeadsCraftLikes;
import com.rumor.yumback.domains.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BeadsCraftLikesJpaRepository extends JpaRepository<BeadsCraftLikes, UUID> {
    Optional<BeadsCraftLikes> findByBeadsCraftAndUser(BeadsCraft beadsCraft, User user);
}
