package com.rumor.yumback.domains.posts.infrastructure;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rumor.yumback.domains.comments.domain.Comment;
import com.rumor.yumback.domains.comments.domain.QComment;
import com.rumor.yumback.domains.posts.application.PostDto;
import com.rumor.yumback.domains.posts.application.QPostDto;
import com.rumor.yumback.domains.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.rumor.yumback.domains.comments.domain.QComment.comment;
import static com.rumor.yumback.domains.posts.domain.QPost.post;
import static com.rumor.yumback.domains.posts.domain.QPostLikes.postLikes;
import static com.rumor.yumback.domains.users.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class PostQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PostDto posts(User loginUser, UUID id) {
        Long likesCount = jpaQueryFactory
                .select(postLikes.id.count())
                .from(postLikes)
                .where(postLikes.id.eq(id))
                .fetchOne();

        return jpaQueryFactory
                .select(Projections.constructor(PostDto.class,
                        post.id,
                        post.title,
                        post.category,
                        post.description,
                        post.contents,
                        post.creator,
                        post.viewCount,
                        Expressions.asNumber(likesCount == null ? 0 : likesCount).as("likesCount"),
                        Expressions.cases()
                                .when(postLikes.user.eq(loginUser)).then(true)
                                .otherwise(false).as("isLiked"),
                        Projections.list(Projections.constructor(Comment.class,
                                comment.id, comment.contents, comment.post, comment.creator, comment.createdAt, comment.updatedAt)),
                        post.createdAt,
                        post.updatedAt
                )).from(post)
                .leftJoin(postLikes).on(post.id.eq(postLikes.id))
                .leftJoin(comment).on(comment.post.id.eq(post.id))
                .innerJoin(user).on(post.creator.id.eq(user.id))
                .where(post.id.eq(id))
                .fetchOne();
    }
}
