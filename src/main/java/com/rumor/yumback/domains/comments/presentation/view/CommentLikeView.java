package com.rumor.yumback.domains.comments.presentation.view;

import com.rumor.yumback.domains.comments.domain.CommentLike;
import com.rumor.yumback.domains.users.presentation.view.UserView;

import java.util.UUID;

public record CommentLikeView(
        UUID id,
        UUID commentId,
        UserView userView
) {
    static CommentLikeView from(CommentLike commentLike) {
        return new CommentLikeView(commentLike.getId(), commentLike.getComment().getId(), UserView.from(commentLike.getUser()));
    }

}
