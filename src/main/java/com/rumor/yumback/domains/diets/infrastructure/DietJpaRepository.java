package com.rumor.yumback.domains.diets.infrastructure;

import com.rumor.yumback.domains.diets.domain.Diet;
import com.rumor.yumback.domains.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DietJpaRepository extends JpaRepository<Diet, UUID> {

    List<Diet> findAllByCreator(User creator);
}
