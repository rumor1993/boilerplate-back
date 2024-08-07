package com.rumor.yumback.domains.comments.application;


import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.comments.domain.CommentLike;
import com.rumor.yumback.domains.comments.domain.Reply;
import com.rumor.yumback.domains.comments.domain.ReplyLike;
import com.rumor.yumback.domains.comments.infrastructure.CommentJpaRepository;
import com.rumor.yumback.domains.comments.infrastructure.CommentLikeJpaRepository;
import com.rumor.yumback.domains.comments.infrastructure.ReplyJpaRepository;
import com.rumor.yumback.domains.comments.infrastructure.ReplyLikeJpaRepository;
import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.posts.infrastructure.PostJpaRepository;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.infrastructure.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentJpaRepository commentJpaRepository;
    private final CommentLikeJpaRepository commentLikeJpaRepository;
    private final ReplyJpaRepository replyJpaRepository;
    private final ReplyLikeJpaRepository replyLikeJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public Comment register(CommentDto commentDto, String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Post foundPost = postJpaRepository.findById(commentDto.postId())
                .orElseThrow(() -> new RuntimeException("not found post"));

        return commentJpaRepository.save(new Comment(commentDto.contents(), foundUser, foundPost));
    }

    public Reply reply(UUID commentId, CommentDto commentDto, String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Comment foundComment = commentJpaRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("not found post"));

        return replyJpaRepository.save(new Reply(commentDto.contents(), foundUser, foundComment));
    }

    public String likes(String username, UUID id) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));


        Comment foundComment = commentJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found post"));

        return commentLikeJpaRepository.findByCommentAndUser(foundComment, foundUser)
                .map(Like -> {
                    commentLikeJpaRepository.delete(Like);
                    return "좋아요가 취소되었습니다.";
                })
                .orElseGet(() -> {
                    commentLikeJpaRepository.save(new CommentLike(foundComment, foundUser));
                    return "좋아요를 누르셨습니다.";
                });
    }

    public String replyLike(String username, UUID id) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Reply foundReply = replyJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found post"));

        return replyLikeJpaRepository.findByReplyAndUser(foundReply, foundUser)
                .map(Like -> {
                    replyLikeJpaRepository.delete(Like);
                    return "좋아요가 취소되었습니다.";
                })
                .orElseGet(() -> {
                    replyLikeJpaRepository.save(new ReplyLike(foundReply, foundUser));
                    return "좋아요를 누르셨습니다.";
                });
    }
}
