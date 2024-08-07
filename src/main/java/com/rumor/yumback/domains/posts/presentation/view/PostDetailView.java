package com.rumor.yumback.domains.posts.presentation.view;

import com.rumor.yumback.domains.comments.presentation.view.CommentView;
import com.rumor.yumback.domains.posts.application.PostDetailDto;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.presentation.view.UserView;
import com.rumor.yumback.enumeration.PostCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostDetailView(
        UUID id,
        String title,
        PostCategory category,
        String contents,
        UserView creator,
        Long viewCount,
        Long likeCount,
        Boolean isLiked,
        List<CommentView> comments,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static PostDetailView from(PostDetailDto postDetailDto) {
        return PostDetailView.from(postDetailDto, null);
    }

    public static PostDetailView from(PostDetailDto postDetailDto, User loginUser) {
        return new PostDetailView(
                postDetailDto.id(),
                postDetailDto.title(),
                postDetailDto.category(),
                postDetailDto.contents(),
                UserView.from(postDetailDto.creator()),
                postDetailDto.viewCount(),
                postDetailDto.likeCount(),
                postDetailDto.isLiked(),
                postDetailDto.comments().stream()
                        .map(comment -> new CommentView(comment, loginUser))
                        .toList(),
                postDetailDto.createdAt(),
                postDetailDto.updatedAt()
        );
    }

    public Long getCommentCount() {
        Long totalCount = 0L;

        for (CommentView comment : comments) {
            totalCount++;
            totalCount += comment.getRepliesCount();
        }

        return totalCount;
    }
}

