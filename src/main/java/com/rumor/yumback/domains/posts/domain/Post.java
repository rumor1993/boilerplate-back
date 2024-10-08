package com.rumor.yumback.domains.posts.domain;


import com.rumor.yumback.common.AuditableEntity;
import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.enumeration.PostCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    @Enumerated(EnumType.STRING)
    private PostCategory category;
    private String description;
    @Column(columnDefinition = "TEXT")
    private String contents;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;
    @Column(name="view_count", columnDefinition = "bigint default 0")
    private Long viewCount = 0L;
    @Column(name="like_count", columnDefinition = "bigint default 0")
    private Long likeCount = 0L;
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    @OneToMany(mappedBy = "post")
    @OrderBy("createdAt asc")
    private List<Comment> comments = new ArrayList<>();


    public Post(String title, PostCategory category, String description, String contents, User creator) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.contents = contents;
        this.creator = creator;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void deleted() {
        this.isDeleted = true;
    }


}
