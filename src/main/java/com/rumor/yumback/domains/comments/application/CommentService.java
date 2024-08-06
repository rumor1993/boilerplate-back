package com.rumor.yumback.domains.comments.application;


import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.comments.domain.CommentLikes;
import com.rumor.yumback.domains.comments.domain.ReComment;
import com.rumor.yumback.domains.comments.domain.ReCommentLikes;
import com.rumor.yumback.domains.comments.infrastructure.CommentJpaRepository;
import com.rumor.yumback.domains.comments.infrastructure.CommentLikeJpaRepository;
import com.rumor.yumback.domains.comments.infrastructure.ReCommentJpaRepository;
import com.rumor.yumback.domains.comments.infrastructure.ReCommentLikeJpaRepository;
import com.rumor.yumback.domains.posts.domain.Post;
import com.rumor.yumback.domains.posts.infrastructure.PostJpaRepository;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.infrastructure.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentJpaRepository commentJpaRepository;
    private final CommentLikeJpaRepository commentLikeJpaRepository;
    private final ReCommentJpaRepository reCommentJpaRepository;
    private final ReCommentLikeJpaRepository reCommentLikeJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public Comment register(CommentDto commentDto, String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Post foundPost = postJpaRepository.findById(commentDto.postId())
                .orElseThrow(() -> new RuntimeException("not found post"));

        return commentJpaRepository.save(new Comment(commentDto.contents(), foundUser, foundPost));
    }

    public ReComment reply(UUID commentId, CommentDto commentDto, String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Comment foundComment = commentJpaRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("not found post"));

        return reCommentJpaRepository.save(new ReComment(commentDto.contents(), foundUser, foundComment));
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
                    commentLikeJpaRepository.save(new CommentLikes(foundComment, foundUser));
                    return "좋아요를 누르셨습니다.";
                });
    }

    public String reCommentLikes(String username, UUID id) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        ReComment foundReComment = reCommentJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found post"));

        return reCommentLikeJpaRepository.findByReCommentAndUser(foundReComment, foundUser)
                .map(Like -> {
                    reCommentLikeJpaRepository.delete(Like);
                    return "좋아요가 취소되었습니다.";
                })
                .orElseGet(() -> {
                    reCommentLikeJpaRepository.save(new ReCommentLikes(foundReComment, foundUser));
                    return "좋아요를 누르셨습니다.";
                });
    }
}
