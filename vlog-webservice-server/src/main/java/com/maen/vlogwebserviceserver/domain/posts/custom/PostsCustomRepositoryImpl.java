package com.maen.vlogwebserviceserver.domain.posts.custom;

import com.maen.vlogwebserviceserver.domain.posts.Posts;
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
    private final int limitSize = 12;

    // 인기순정렬 메인화면
    @Override
    public List<Posts> findPopularListInMainPage(Integer pageNumber) {
        return jpaQueryFactory
                .selectFrom(posts)
                .orderBy(posts.views.desc())
                .limit(limitSize)
                .offset(pageNumber * limitSize)
                .fetch();
    }

    // 인기순정렬 검색시
    @Override
    public List<Posts> findPopularListByTagSearch(String tag, Integer pageNumber) {
        return jpaQueryFactory
                .selectFrom(posts)
                .from(posts)
                .join(postsTags).on(posts.id.eq(postsTags.postsId))
                .join(tags).on(postsTags.tagsId.eq(tags.id))
                .where(
                        tags.content.contains(tag)
                )
                .orderBy(posts.views.desc())
                .limit(limitSize)
                .offset(pageNumber * limitSize)
                .fetch();
    }

    // 최신순정렬 메인화면
    @Override
    public List<Posts> findRecentListInMainPage(Long lastPostId) {
        return jpaQueryFactory
                .selectFrom(posts)
                .where(
                        ltPostsId(lastPostId)
                )
                .orderBy(posts.id.desc())
                .limit(limitSize)
                .fetch();
    }
    //최신순 정렬 검색시
    @Override
    public List<Posts> findRecentListByTagSearch(String tag, Long lastPostId) {
        return jpaQueryFactory.select(posts)
                .from(posts)
                .join(postsTags).on(posts.id.eq(postsTags.postsId))
                .join(tags).on(postsTags.tagsId.eq(tags.id))
                .where(
                        tags.content.contains(tag),
                        ltPostsId(lastPostId)
                )
                .orderBy(posts.id.desc())
                .limit(limitSize)
                .fetch();
    }

    private BooleanExpression ltPostsId(Long postsId) {
        if(postsId == null) {
            return null;
        }
        return posts.id.lt(postsId);
    }

}
