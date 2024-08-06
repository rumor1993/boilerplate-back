package com.rumor.yumback.domains.comments.presentation.view;

import com.querydsl.core.annotations.QueryProjection;
import com.rumor.yumback.domains.comments.domain.Comment;
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
    private final List<ReCommentView> reply;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Boolean isReComment = false;

    @QueryProjection
    public CommentView(Comment savedComment) {
        this.id = savedComment.getId();
        this.contents = savedComment.getContents();
        this.creator = UserView.of(savedComment.getCreator());
        this.postId = savedComment.getPost().getId();
        this.createdAt = savedComment.getCreatedAt();
        this.updatedAt = savedComment.getUpdatedAt();
        this.reply = savedComment.getReComments().stream()
                .map(ReCommentView::new)
                .toList();
    }

    public Integer getLikeCount() {
        return 0;
    }

    public Integer getCommentCount() {
        return reply.size();
    }
}
