package com.rumor.yumback.domains.comments.domain;


import com.rumor.yumback.common.AuditableEntity;
import com.rumor.yumback.domains.users.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "reply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @OneToMany(mappedBy = "reply")
    private List<ReplyLike> likes = new ArrayList<>();

    public Reply(String contents, User creator, Comment comment) {
        this.contents = contents;
        this.creator = creator;
        this.comment = comment;
    }
}
