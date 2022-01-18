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
        return jpaQueryFactory
                .selectFrom(posts)
                .where(
                        isNullPostsId(postsId)
                )
                .orderBy(posts.id.desc())
                .limit(12)
                .fetch();
    }

    private BooleanExpression isNullPostsId(Long postsId) {
        if(postsId == null) {
            return null;
        }
        return posts.id.gt(postsId);
    }
}
