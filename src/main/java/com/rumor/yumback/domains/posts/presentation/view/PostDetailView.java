package com.rumor.yumback.domains.posts.presentation.view;

import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.comments.presentation.view.CommentView;
import com.rumor.yumback.domains.posts.application.PostDetailDto;
import com.rumor.yumback.enumeration.PostCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostDetailView(
        UUID id,
        String title,
        PostCategory category,
        String contents,
        String username,
        Long viewCount,
        Long commentCount,
        Long likeCount,
        Boolean isLiked,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<CommentView> comments
) {

    public static PostDetailView from(PostDetailDto postDetailDto, List<Comment> contents) {
        return new PostDetailView(
                postDetailDto.id(),
                postDetailDto.title(),
                postDetailDto.category(),
                postDetailDto.contents(),
                postDetailDto.username(),
                postDetailDto.viewCount(),
                postDetailDto.commentCount(),
                postDetailDto.likeCount(),
                postDetailDto.isLiked(),
                postDetailDto.createdAt(),
                postDetailDto.updatedAt(),
                contents.stream()
                        .map(CommentView::new)
                        .toList()
        );
    }
}

