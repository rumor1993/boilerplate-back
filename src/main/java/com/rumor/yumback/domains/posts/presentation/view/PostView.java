package com.rumor.yumback.domains.posts.presentation.view;

import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.comments.presentation.view.CommentView;
import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.users.presentation.view.UserView;
import com.rumor.yumback.enumeration.PostCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostView(
        UUID id,
        String title,
        PostCategory category,
        String description,
        String contents,
        UserView creator,
        Long viewCount,
        Long commentCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public PostView(UUID id, String title, PostCategory category, String description, String contents, UserView creator, Long viewCount, Long commentCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.contents = contents;
        this.creator = creator;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostView of(Post post) {
        return new PostView(post.getId(), post.getTitle(), post.getCategory(), post.getDescription(), post.getContents(), UserView.of(post.getCreator()), post.getViewCount(),
                commentCount(post.getComments()), post.getCreatedAt(), post.getUpdatedAt());
    }

    public static Long commentCount(List<Comment> comments) {
        int totalCount = comments.size();
        for (Comment comment : comments) {
            totalCount += comment.getReComments().size();
        }
        return (long) totalCount;
    }
}
