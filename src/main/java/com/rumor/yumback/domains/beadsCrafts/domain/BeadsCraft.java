package com.rumor.yumback.domains.beadsCrafts.domain;

import com.rumor.yumback.common.AuditableEntity;
import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.enumeration.BeadCraftCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "beads_craft")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeadsCraft extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private UUID id;

    private String name;

    private String link;

    @Enumerated(EnumType.STRING)
    private BeadCraftCategory category;

    private String picture;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @OneToMany(mappedBy = "beadsCraft")
    private List<BeadsCraftLikes> likes = new ArrayList<>();

    @Column(name = "like_count", columnDefinition = "bigint default 0")
    private Long likeCount = 0L;

    public BeadsCraft(String name, String link, BeadCraftCategory category, String picture, Post post, User foundUser) {
        this.name = name;
        this.link = link;
        this.category = category;
        this.picture = picture;
        this.post = post;
        this.creator = foundUser;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void changePicture(String uriString) {
        this.picture = uriString;
    }
}
