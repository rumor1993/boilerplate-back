package com.rumor.yumback.domains.comments.domain;


import com.rumor.yumback.common.AuditableEntity;
import com.rumor.yumback.domains.users.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "recomment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReComment extends AuditableEntity {

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

    public ReComment(String contents, User creator, Comment comment) {
        this.contents = contents;
        this.creator = creator;
        this.comment = comment;
    }
}
