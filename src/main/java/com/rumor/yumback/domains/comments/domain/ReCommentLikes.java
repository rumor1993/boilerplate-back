package com.rumor.yumback.domains.comments.domain;


import com.rumor.yumback.domains.users.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "reomment_likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReCommentLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "recomment_id")
    private ReComment reComment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ReCommentLikes(ReComment reComment, User user) {
        this.reComment = reComment;
        this.user = user;
    }
}
