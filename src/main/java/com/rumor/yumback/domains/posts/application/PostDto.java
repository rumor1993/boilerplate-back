package com.rumor.yumback.domains.posts.application;

import com.querydsl.core.annotations.QueryProjection;
import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.enumeration.PostCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostDto(
        UUID id,
        String title,
        PostCategory category,
        String description,
        String contents,
        User creator,
        Long viewCount,
        Long likesCount,
        Boolean isLiked,
        List<Comment> comments,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    @QueryProjection
    public PostDto(UUID id, String title, PostCategory category, String description, String contents, User creator, Long viewCount, Long likesCount, Boolean isLiked, List<Comment> comments, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.contents = contents;
        this.creator = creator;
        this.viewCount = viewCount;
        this.likesCount = likesCount;
        this.isLiked = isLiked;
        this.comments = comments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostDto of (Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getCategory(),
                post.getDescription(),
                post.getContents(),
                post.getCreator(),
                post.getViewCount(),
                null,
                null,
                post.getComments(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    public static PostDto of(Post post,  Long likesCount, Boolean isLiked) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getCategory(),
                post.getDescription(),
                post.getContents(),
                post.getCreator(),
                post.getViewCount(),
                likesCount,
                isLiked,
                post.getComments(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}