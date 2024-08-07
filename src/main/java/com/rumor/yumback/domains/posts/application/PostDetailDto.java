package com.rumor.yumback.domains.posts.application;

import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.enumeration.PostCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostDetailDto(
        UUID id,
        String title,
        PostCategory category,
        String contents,
        User creator,
        Long viewCount,
        Long likeCount,
        Boolean isLiked,
        List<Comment> comments,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static PostDetailDto from(Post post) {
        return PostDetailDto.from(post, false);
    }

    public static PostDetailDto from(Post post, Boolean isLiked) {
        return new PostDetailDto(
                post.getId(),
                post.getTitle(),
                post.getCategory(),
                post.getContents(),
                post.getCreator(),
                post.getViewCount(),
                post.getLikeCount(),
                isLiked,
                post.getComments(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}

