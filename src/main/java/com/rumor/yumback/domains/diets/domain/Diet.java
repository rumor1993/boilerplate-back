package com.rumor.yumback.domains.diets.domain;

import com.rumor.yumback.common.AuditableEntity;
import com.rumor.yumback.domains.users.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "diet")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diet extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    private String name;

    private String picture;

    private Integer calorie;

    public Diet(User creator, String name, String picture, Integer calorie) {
        this.creator = creator;
        this.name = name;
        this.picture = picture;
        this.calorie = calorie;
    }
}
