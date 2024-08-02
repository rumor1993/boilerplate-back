package com.rumor.yumback.domains.posts.presentation.view;

import com.rumor.yumback.domains.comments.presentation.view.CommentView;
import com.rumor.yumback.domains.posts.application.PostDto;
import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.users.presentation.view.UserView;
import com.rumor.yumback.enumeration.PostCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostDetailView(
        UUID id,
        String title,
        PostCategory category,
        String description,
        String contents,
        UserView creator,
        Long viewCount,
        Long likesCount,
        Boolean isLiked,
        List<CommentView> comments,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public PostDetailView(UUID id, String title, PostCategory category, String description, String contents, UserView creator, Long viewCount, Long likesCount, Boolean isLiked, List<CommentView> comments, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public static PostDetailView of(PostDto postDto) {
        return new PostDetailView(
                postDto.id(), postDto.title(), postDto.category(), postDto.description(), postDto.contents(),
                UserView.of(postDto.creator()), postDto.viewCount(), postDto.likesCount(), postDto.isLiked(),
                postDto.comments().stream()
                        .map(CommentView::new)
                        .toList(),
                postDto.createdAt(), postDto.updatedAt()
        );
    }

    public Integer getCommentCount() {
        int totalCount = comments.size();
        for (CommentView comment : comments) {
            totalCount += comment.getReply().size();
        }
        return totalCount;
    }
}

