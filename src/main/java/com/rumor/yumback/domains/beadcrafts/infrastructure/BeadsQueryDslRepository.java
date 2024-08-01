package com.rumor.yumback.domains.beadcrafts.infrastructure;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rumor.yumback.domains.beadcrafts.domain.BeadCraft;
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
        List<BeadCraftView> beadCraftViews = jpaQueryFactory
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
                                beadCraftLikes.user.eq(loginUser).as("isLiked"),
                                beadCraft.createdAt,
                                beadCraft.updatedAt))
                .from(beadCraft)
                .leftJoin(beadCraftLikes).on(beadCraft.id.eq(beadCraftLikes.id))
                .innerJoin(user).on(beadCraft.creator.id.eq(user.id))
                .fetchJoin()
                .where((category == null || category == BeadCraftCategory.ALL) ? Expressions.TRUE : beadCraft.category.eq(category))
                .groupBy(beadCraft.id, beadCraft.name, beadCraft.category, beadCraft.picture, user.name)
                .fetch();


        JPAQuery<Long> count = jpaQueryFactory.select(beadCraft.count())
                .from(beadCraft);

        return PageableExecutionUtils.getPage(beadCraftViews, pageable, count::fetchOne);
    }
}
