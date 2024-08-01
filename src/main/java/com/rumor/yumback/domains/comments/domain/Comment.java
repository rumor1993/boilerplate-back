package com.rumor.yumback.domains.comments.domain;

import com.rumor.yumback.common.AuditableEntity;
import com.rumor.yumback.domains.posts.domain.Post;
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
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String contents;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @OneToMany(mappedBy = "comment")
    private List<ReComment> reComments = new ArrayList<>();

    public Comment(String contents, User creator, Post post) {
        this.contents = contents;
        this.creator = creator;
        this.post = post;
    }
}
