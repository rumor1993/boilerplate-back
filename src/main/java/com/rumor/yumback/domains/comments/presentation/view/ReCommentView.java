package com.rumor.yumback.domains.comments.presentation.view;

import com.rumor.yumback.domains.comments.domain.ReComment;
import com.rumor.yumback.domains.users.presentation.view.UserView;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ReCommentView {
    private final UUID id;
    private final String contents;
    private final UserView creator;
    private final UUID commentId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ReCommentView(ReComment reComment) {
        this.id = reComment.getId();
        this.contents = reComment.getContents();
        this.creator = UserView.of(reComment.getCreator());
        this.commentId = reComment.getComment().getId();
        this.createdAt = reComment.getCreatedAt();
        this.updatedAt = reComment.getUpdatedAt();
    }
}
