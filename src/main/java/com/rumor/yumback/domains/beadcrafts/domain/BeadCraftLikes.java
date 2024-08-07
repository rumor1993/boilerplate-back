package com.rumor.yumback.domains.beadcrafts.domain;

import com.rumor.yumback.common.AuditableEntity;
import com.rumor.yumback.domains.users.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "beadcrafts_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeadCraftLikes extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "beadcraft_id")
    private BeadCraft beadCraft;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BeadCraftLikes(BeadCraft beadCraft, User user) {
        this.beadCraft = beadCraft;
        this.user = user;
    }
}
