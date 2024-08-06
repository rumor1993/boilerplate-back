package com.rumor.yumback.domains.posts.infrastructure;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rumor.yumback.domains.comments.domain.QReComment;
import com.rumor.yumback.domains.posts.application.PostDetailDto;
import com.rumor.yumback.domains.posts.presentation.view.PostView;
import com.rumor.yumback.domains.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.rumor.yumback.domains.comments.domain.QComment.comment;
import static com.rumor.yumback.domains.comments.domain.QReComment.*;
import static com.rumor.yumback.domains.posts.domain.QPost.post;
import static com.rumor.yumback.domains.posts.domain.QPostLikes.postLikes;
import static com.rumor.yumback.domains.users.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class PostQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PostDetailDto post(User loginUser, UUID postId) {
        NumberExpression<Long> commentCount = comment.id.countDistinct();
        NumberExpression<Long> reCommentCount = reComment.id.countDistinct();
        NumberExpression<Long> totalCommentCount = commentCount.add(reCommentCount);


        return jpaQueryFactory
                .select(Projections.constructor(PostDetailDto.class,
                        post.id,
                        post.title,
                        post.category,
                        post.contents,
                        user.name.as("username"),
                        post.viewCount,
                        totalCommentCount.as("commentCount"),
                        postLikes.id.countDistinct().as("likeCount"),
                        Expressions.cases()
                                .when(postLikes.user.eq(loginUser)).then(true)
                                .otherwise(false).as("isLiked"),
                        post.createdAt,
                        post.updatedAt
                ))
                .from(post)
                .innerJoin(post.creator, user)
                .leftJoin(postLikes).on(post.id.eq(postLikes.post.id))
                .leftJoin(comment).on(post.id.eq(comment.post.id))
                .leftJoin(reComment).on(comment.id.eq(reComment.comment.id))
                .groupBy(post.id, post.title, post.category, user.name, postLikes.user, post.createdAt, post.updatedAt)
                .where(post.id.eq(postId))
                .fetchOne();
    }

    public Page<PostView> posts(Pageable pageable) {
        JPAQuery<Long> count = jpaQueryFactory
                .select(post.id.countDistinct())
                .from(post)
                .innerJoin(post.creator, user)
                .leftJoin(postLikes).on(post.id.eq(postLikes.post.id))
                .leftJoin(comment).on(post.id.eq(comment.post.id));

        List<PostView> posts = jpaQueryFactory
                .select(Projections.constructor(PostView.class,
                        post.id,
                        post.title,
                        post.category,
                        post.contents,
                        user.name.as("username"),
                        post.viewCount,
                        comment.id.countDistinct().as("commentCount"),
                        postLikes.id.countDistinct().as("likeCount"),
                        post.createdAt,
                        post.updatedAt
                ))
                .from(post)
                .innerJoin(post.creator, user)
                .leftJoin(postLikes).on(post.id.eq(postLikes.post.id))
                .leftJoin(comment).on(post.id.eq(comment.post.id))
                .groupBy(post.id, post.title, post.category, post.description, user.name, post.createdAt, post.updatedAt)
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(posts, pageable, count::fetchOne);
    }

    public List<PostView> populars3() {
        return jpaQueryFactory
                .select(Projections.constructor(PostView.class,
                        post.id,
                        post.title,
                        post.category,
                        post.contents,
                        user.name.as("username"),
                        post.viewCount,
                        comment.id.countDistinct().as("commentCount"),
                        postLikes.id.countDistinct().as("likeCount"),
                        post.createdAt,
                        post.updatedAt
                ))
                .from(post)
                .innerJoin(post.creator, user)
                .leftJoin(postLikes).on(post.id.eq(postLikes.post.id))
                .leftJoin(comment).on(post.id.eq(comment.post.id))
                .groupBy(post.id, post.title, post.category, user.name, post.createdAt, post.updatedAt)
                .orderBy(postLikes.id.countDistinct().desc().nullsLast()) // Ordering by likeCount
                .limit(3)
                .fetch();
    }
}
