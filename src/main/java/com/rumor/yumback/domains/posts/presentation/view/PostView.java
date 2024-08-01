package com.rumor.yumback.domains.posts.presentation.view;

import com.rumor.yumback.domains.comments.presentation.view.CommentView;
import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.users.presentation.view.UserView;
import com.rumor.yumback.enumeration.PostCategory;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class PostView {
    private final UUID id;
    private final String title;
    private final PostCategory category;
    private final String description;
    private final String contents;
    private final UserView creator;
    private final List<CommentView> comments;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private PostView(UUID id, String title, PostCategory category, String description, String contents, UserView creator, List<CommentView> comments, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.contents = contents;
        this.creator = creator;
        this.comments = comments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostView of(Post post) {
        List<CommentView> commentViews = post.getComments().stream()
                .map(CommentView::new)
                .collect(Collectors.toList());

        return new PostView(post.getId(), post.getTitle(), post.getCategory(),
                post.getDescription(), post.getContents(), UserView.of(post.getCreator()),
                commentViews, post.getCreatedAt(), post.getUpdatedAt());
    }

    public Integer getCommentCount() {
        int totalCount = comments.size();
        for (CommentView comment : comments) {
            totalCount += comment.getReply().size();
        }
        return totalCount;
    }
}

