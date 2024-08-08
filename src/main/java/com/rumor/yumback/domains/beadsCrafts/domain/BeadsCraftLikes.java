package com.rumor.yumback.domains.beadsCrafts.domain;

import com.rumor.yumback.common.AuditableEntity;
import com.rumor.yumback.domains.users.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "beads_craft_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeadsCraftLikes extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "beads_craft_id")
    private BeadsCraft beadsCraft;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BeadsCraftLikes(BeadsCraft beadsCraft, User user) {
        this.beadsCraft = beadsCraft;
        this.user = user;
    }
}
