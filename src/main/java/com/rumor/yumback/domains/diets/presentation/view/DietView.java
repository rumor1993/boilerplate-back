package com.rumor.yumback.domains.diets.presentation.view;

import com.rumor.yumback.domains.diets.domain.Diet;
import com.rumor.yumback.domains.users.presentation.view.UserView;

import java.time.LocalDateTime;
import java.util.UUID;

public record DietView(
        UUID id,
        UserView creator,
        String name,
        String picture,
        Integer calorie,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static DietView of(Diet diet) {
        return new DietView(diet.getId(), UserView.from(diet.getCreator()), diet.getName(), diet.getPicture(), diet.getCalorie(), diet.getCreatedAt(), diet.getUpdatedAt());
    }
}
