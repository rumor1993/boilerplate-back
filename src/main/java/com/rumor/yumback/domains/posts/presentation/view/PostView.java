package com.rumor.yumback.domains.posts.presentation.view;

import com.rumor.yumback.domains.posts.application.PostDto;
import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.enumeration.PostCategory;
import com.rumor.yumback.utils.PostUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostView(
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
    public static PostView from(PostDto postDto) {
        return new PostView(
                postDto.id(),
                postDto.title(),
                postDto.category(),
                postDto.description(),
                postDto.contents(),
                postDto.creator(),
                postDto.commentCount(),
                postDto.viewCount(),
                postDto.likeCount(),
                postDto.isLiked(),
                postDto.isDeleted(),
                postDto.createdAt(),
                postDto.updatedAt()
        );
    }

    public String getImage() {
        return PostUtils.extractImage(this.contents);
    }

    public String getDescription() {
        return PostUtils.extractDescription(this.contents, 200);
    }

}
