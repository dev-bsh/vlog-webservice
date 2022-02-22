package com.maen.vlogwebserviceserver.domain.user.custom;

import com.maen.vlogwebserviceserver.domain.user.Follows;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.maen.vlogwebserviceserver.domain.user.QFollows.follows;

@RequiredArgsConstructor
@Repository
public class FollowsCustomRepositoryImpl implements FollowsCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final int limitSize = 12;

    @Override
    public List<Follows> findFollowerList(Long userId, Long lastFollowsId) {
        return jpaQueryFactory
                .selectFrom(follows)
                .where(
                        follows.followTargetId.eq(userId),
                        ltLastId(lastFollowsId)
                )
                .orderBy(follows.id.desc())
                .limit(limitSize)
                .fetch();
    }

    @Override
    public List<Follows> findFollowingList(Long userId, Long lastFollowsId) {
        return jpaQueryFactory
                .selectFrom(follows)
                .where(
                        follows.userId.eq(userId),
                        ltLastId(lastFollowsId)
                )
                .orderBy(follows.id.desc())
                .limit(limitSize)
                .fetch();
    }

    private BooleanExpression ltLastId(Long lastId) {
        if(lastId == null) {
            return null;
        }
        else {
            return follows.id.lt(lastId);
        }
    }
}
