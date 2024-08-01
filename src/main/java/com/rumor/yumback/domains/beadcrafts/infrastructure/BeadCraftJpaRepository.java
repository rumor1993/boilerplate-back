package com.rumor.yumback.domains.beadcrafts.infrastructure;

import com.rumor.yumback.domains.beadcrafts.application.BeadCraftDto;
import com.rumor.yumback.domains.beadcrafts.domain.BeadCraft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BeadCraftJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<BeadCraft, UUID> {

    @Query(value = "SELECT " +
            "b.id AS beadCraftId, " +
            "b.name AS beadCraftName, " +
            "b.category AS beadCraftCategory, " +
            "b.picture AS beadCraftPicture, " +
            "b.created_at AS createdAt, " +
            "b.updated_at AS updatedAt, " +
            "u.name AS creatorName, " +   // Creator's name
            "COUNT(bl.id) AS likeCount, " +
            "MAX(CASE WHEN bl.user_id = :userId THEN 'true' ELSE 'false' END) AS isLiked " +
            "FROM beadcrafts b " +
            "LEFT JOIN beadcrafts_likes bl ON b.id = bl.beadcrafts_id " +
            "INNER JOIN users u ON b.user_id = u.id " +
            "GROUP BY b.id, b.name, b.category, b.picture, u.name",
            countQuery = "SELECT COUNT(0) FROM beadcrafts b " +
                    "LEFT JOIN beadcrafts_likes bl ON b.id = bl.beadcrafts_id " +
                    "INNER JOIN users u ON b.user_id = u.id",
            nativeQuery = true)
    Page<BeadCraftDto> findBeadCraftsWithDetailsAndLikes(@Param("userId") UUID userId, Pageable pageable);
}
