package com.rumor.yumback.domains.comments.presentation.view;

import com.rumor.yumback.domains.comments.domain.CommentLike;
import com.rumor.yumback.domains.comments.domain.ReplyLike;
import com.rumor.yumback.domains.users.presentation.view.UserView;

import java.util.UUID;

public record ReplyLikeView(
        UUID id,
        UUID replyId,
        UserView userView
) {
    static ReplyLikeView from(ReplyLike replyLike) {
        return new ReplyLikeView(replyLike.getId(), replyLike.getReply().getId(), UserView.from(replyLike.getUser()));
    }

}
