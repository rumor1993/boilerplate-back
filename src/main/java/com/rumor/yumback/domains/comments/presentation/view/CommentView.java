package com.rumor.yumback.domains.comments.presentation.view;

import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.presentation.view.UserView;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class CommentView {
    private final UUID id;
    private final String contents;
    private final UserView creator;
    private final UUID postId;
    private final List<ReplyView> replies;
    private final List<CommentLikeView> likes;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Boolean isReply = false;
    private Boolean isLiked = false;

    public CommentView(Comment comment) {
        this(comment, null);
    }

    public CommentView(Comment comment, User loginUser) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.creator = UserView.from(comment.getCreator());
        this.postId = comment.getPost().getId();
        this.likes = comment.getLikes().stream()
                .map(CommentLikeView::from)
                .toList();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.replies = comment.getReplies().stream()
                .map(reply -> new ReplyView(reply, loginUser))
                .toList();

        if (loginUser != null) {
            this.isLiked = this.likes.stream()
                    .anyMatch(like -> loginUser.getId().equals(like.userView().id()));
        }
    }

    public Long getLikeCount() {
        return 0L;
    }

    public Long getRepliesCount() {
        return (long) replies.size();
    }
}
