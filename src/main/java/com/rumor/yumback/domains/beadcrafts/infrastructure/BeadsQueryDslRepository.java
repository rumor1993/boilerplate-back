package com.rumor.yumback.domains.beadcrafts.infrastructure;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rumor.yumback.domains.beadcrafts.domain.BeadCraft;
import com.rumor.yumback.domains.beadcrafts.domain.BeadCraftLikes;
import com.rumor.yumback.domains.beadcrafts.domain.QBeadCraft;
import com.rumor.yumback.domains.beadcrafts.presentation.BeadCraftView;
import com.rumor.yumback.domains.beadcrafts.presentation.QBeadCraftView;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.presentation.view.QUserView;
import com.rumor.yumback.enumeration.BeadCraftCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.rumor.yumback.domains.beadcrafts.domain.QBeadCraft.beadCraft;
import static com.rumor.yumback.domains.beadcrafts.domain.QBeadCraftLikes.beadCraftLikes;
import static com.rumor.yumback.domains.users.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class BeadsQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<BeadCraftView> findBeadCrafts(User loginUser, Pageable pageable, BeadCraftCategory category) {
        JPAQuery<BeadCraftView> beadCraftQuery = jpaQueryFactory
                .select(
                        new QBeadCraftView(
                                beadCraft.id,
                                beadCraft.name,
                                beadCraft.category,
                                beadCraft.picture,
                                new QUserView(
                                        beadCraft.creator.id.as("id"),
                                        beadCraft.creator.username.as("username"),
                                        beadCraft.creator.name.as("name"),
                                        beadCraft.creator.email.as("email"),
                                        beadCraft.creator.picture.as("picture"),
                                        beadCraft.creator.role.as("role"),
                                        beadCraft.creator.createdAt.as("createdAt"),
                                        beadCraft.creator.updatedAt.as("updatedAt")
                                ),
                                beadCraftLikes.id.count().as("likeCount"),
                                getLiked(loginUser),
                                beadCraft.createdAt,
                                beadCraft.updatedAt))
                .from(beadCraft)
                .leftJoin(beadCraftLikes).on(beadCraft.id.eq(beadCraftLikes.beadCraft.id))
                .innerJoin(user).on(beadCraft.creator.id.eq(user.id))
                .fetchJoin()
                .where((category == null || category == BeadCraftCategory.ALL) ? Expressions.TRUE : beadCraft.category.eq(category))
                .limit(pageable.getPageSize())
                .groupBy(
                        beadCraft.id,
                        beadCraft.name,
                        beadCraft.category,
                        beadCraft.picture,
                        beadCraft.creator.id,
                        beadCraft.creator.username,
                        beadCraft.creator.name,
                        beadCraft.creator.email,
                        beadCraft.creator.picture,
                        beadCraft.creator.role,
                        beadCraft.creator.createdAt,
                        beadCraft.creator.updatedAt,
                        beadCraftLikes.user,
                        beadCraft.createdAt,
                        beadCraft.updatedAt
                );

        for (Sort.Order order : pageable.getSort()) {
            if (order.getProperty().equals("likesCount")) {
                beadCraftQuery.orderBy(new OrderSpecifier<>(Order.DESC, beadCraftLikes.id.count()));
            }

             if (order.getProperty().equals("createdAt")) {
                beadCraftQuery.orderBy(new OrderSpecifier<>(Order.DESC, beadCraft.createdAt));
            }

        }

        JPAQuery<Long> count = jpaQueryFactory.select(beadCraft.count())
                .from(beadCraft);

        return PageableExecutionUtils.getPage(beadCraftQuery.fetch(), pageable, count::fetchOne);
    }

    private static BooleanExpression getLiked(User loginUser) {
        if ( loginUser == null ) {
            return Expressions.FALSE.as("isLiked");
        }

        return Expressions.cases()
                .when(beadCraftLikes.user.eq(loginUser)).then(true)
                .otherwise(false).as("isLiked");
    }

}
