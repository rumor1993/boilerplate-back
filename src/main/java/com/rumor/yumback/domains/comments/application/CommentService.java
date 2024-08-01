package com.rumor.yumback.domains.comments.application;


import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.comments.domain.ReComment;
import com.rumor.yumback.domains.comments.infrastructure.CommentJpaRepository;
import com.rumor.yumback.domains.comments.infrastructure.ReCommentJpaRepository;
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
    private final ReCommentJpaRepository reCommentJpaRepository;
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
}
