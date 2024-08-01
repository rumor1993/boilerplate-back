package com.rumor.yumback.domains.beadcrafts.infrastructure;

import com.rumor.yumback.domains.beadcrafts.domain.BeadCraft;
import com.rumor.yumback.domains.beadcrafts.domain.BeadCraftLikes;
import com.rumor.yumback.domains.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BeadCraftLikesJpaRepository extends JpaRepository<BeadCraftLikes, UUID> {
    Optional<BeadCraftLikes> findByBeadCraftAndUser(BeadCraft beadCraft, User user);
}
