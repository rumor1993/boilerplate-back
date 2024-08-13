package com.rumor.yumback.domains.beadsCrafts.infrastructure;

import com.rumor.yumback.domains.beadsCrafts.application.BeadsCraftDto;
import com.rumor.yumback.domains.beadsCrafts.domain.BeadsCraft;
import com.rumor.yumback.enumeration.BeadCraftCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BeadsCraftJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<BeadsCraft, UUID> {
    Page<BeadsCraft> findAllByCategory(BeadCraftCategory category, Pageable pageable);
}
