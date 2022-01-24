package com.maen.vlogwebserviceserver.domain.comments.custom;

import com.maen.vlogwebserviceserver.domain.comments.Comments;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.maen.vlogwebserviceserver.domain.comments.QComments.comments;

@RequiredArgsConstructor
@Repository
public class CommentsCustomRepositoryImpl implements CommentsCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comments> findAllInPostsDetail(Long postsId, Long commentsId) {
        int nextCommentsListSize = 12;

        return jpaQueryFactory
                .selectFrom(comments)
                .where(
                        comments.postsId.eq(postsId),
                        ltCommentsId(commentsId)
                )
                .orderBy(comments.id.desc())
                .limit(nextCommentsListSize)
                .fetch();
    }

    public BooleanExpression ltCommentsId(Long commentsId) {
        if(commentsId == null) {
            return null;
        }
        return comments.id.lt(commentsId);
    }
}
