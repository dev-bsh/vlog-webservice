package com.maen.vlogwebserviceserver.domain.posts.custom;

import com.maen.vlogwebserviceserver.domain.posts.Posts;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.maen.vlogwebserviceserver.domain.posts.QPosts.posts;
import static com.maen.vlogwebserviceserver.domain.posts.QPostsTags.postsTags;
import static com.maen.vlogwebserviceserver.domain.posts.QTags.tags;

@RequiredArgsConstructor
@Repository
public class PostsCustomRepositoryImpl implements PostsCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    //한번에 불러오는 posts 개수
    private final int nextPostsListSize = 12;

    @Override
    public List<Posts> findAllInMainPage(Long lastPostId, String orderType) {

        return jpaQueryFactory
                .selectFrom(posts)
                .where(
                        ltPostsId(lastPostId)
                )
                .orderBy(orderByType(orderType))
                .limit(nextPostsListSize)
                .fetch();
    }

    @Override
    public List<Posts> findAllByTag(String tag, Long lastPostId, String orderType) {

        return jpaQueryFactory.select(posts)
                .from(posts)
                .join(postsTags).on(posts.id.eq(postsTags.postsId))
                .join(tags).on(postsTags.tagsId.eq(tags.id))
                .where(
                        tags.content.contains(tag),
                        ltPostsId(lastPostId)
                )
                .orderBy(orderByType(orderType))
                .limit(nextPostsListSize)
                .fetch();
    }

    private BooleanExpression ltPostsId(Long postsId) {
        if(postsId == null) {
            return null;
        }
        return posts.id.lt(postsId);
    }

    private OrderSpecifier<?> orderByType(String orderType) {
        if(orderType.equals("popular")) {
            return posts.views.desc();
        }
        else {
            return posts.id.desc();
        }
    }

}
