package com.rumor.yumback.domains.posts.application;

import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.enumeration.PostCategory;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostDto(
        UUID id,
        String title,
        PostCategory category,
        String description,
        String contents,
        User creator,
        Long commentCount,
        Long viewCount,
        Long likeCount,
        Boolean isLiked,
        Boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostDto from(Post post, Boolean isLiked) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getCategory(),
                post.getDescription(),
                post.getContents(),
                post.getCreator(),
                (long) post.getComments().size(),
                post.getViewCount(),
                post.getLikeCount(),
                isLiked,
                post.getIsDeleted(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }

    public static PostDto from(Post post) {
        return PostDto.from(post, false);
    }

}