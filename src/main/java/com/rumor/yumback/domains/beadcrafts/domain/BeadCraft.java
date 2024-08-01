package com.rumor.yumback.domains.beadcrafts.domain;

import com.rumor.yumback.common.AuditableEntity;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.enumeration.BeadCraftCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "beadcrafts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeadCraft extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private BeadCraftCategory category;

    private String picture;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    public BeadCraft(String name, BeadCraftCategory category, String picture, User foundUser) {
        this.name = name;
        this.category = category;
        this.picture = picture;
        this.creator = foundUser;
    }
}
