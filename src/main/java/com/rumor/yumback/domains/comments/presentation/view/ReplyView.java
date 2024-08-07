package com.rumor.yumback.domains.comments.presentation.view;

import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.comments.domain.Reply;
import com.rumor.yumback.domains.comments.domain.ReplyLike;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.presentation.view.UserView;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class ReplyView {
    private final UUID id;
    private final String contents;
    private final UserView creator;
    private final UUID commentId;
    private final List<ReplyLikeView> likes;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Boolean isReply = true;
    private Boolean isLiked = false;

    public ReplyView(Reply reply) {
        this(reply, null);
    }

    public ReplyView(Reply reply, User loginUser) {
        this.id = reply.getId();
        this.contents = reply.getContents();
        this.creator = UserView.from(reply.getCreator());
        this.commentId = reply.getComment().getId();
        this.createdAt = reply.getCreatedAt();
        this.updatedAt = reply.getUpdatedAt();
        this.likes = reply.getLikes().stream()
                .map(ReplyLikeView::from)
                .toList();

        if (loginUser != null) {
            this.isLiked = this.likes.stream()
                    .anyMatch(like -> loginUser.getId().equals(like.userView().id()));
        }
    }
}
