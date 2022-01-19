package com.maen.vlogwebserviceserver.domain.posts.custom;

import com.maen.vlogwebserviceserver.domain.posts.Posts;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.maen.vlogwebserviceserver.domain.posts.QPosts.posts;

@RequiredArgsConstructor
@Repository
public class PostsCustomRepositoryImpl implements PostsCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Posts> findNextPosts(Long postsId) {
        //한번에 불러오는 posts 개수
        int nextPostListSize = 12;

        return jpaQueryFactory
                .selectFrom(posts)
                .where(
                        ltPostsId(postsId)
                )
                .orderBy(posts.id.desc())
                .limit(nextPostListSize)
                .fetch();
    }

    private BooleanExpression ltPostsId(Long postsId) {
        if(postsId == null) {
            return null;
        }
        return posts.id.lt(postsId);
    }
}
