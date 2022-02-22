package com.maen.vlogwebserviceserver.domain.user.custom;

import com.maen.vlogwebserviceserver.domain.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.maen.vlogwebserviceserver.domain.user.QFollows.follows;
import static com.maen.vlogwebserviceserver.domain.user.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final int limitSize = 12;

    @Override
    public List<User> findUserByKeyword(String keyword, Integer pageNumber) {
        return jpaQueryFactory.selectFrom(user)
                .join(follows).on(user.id.eq(follows.followTargetId))
                .where(
                        user.name.contains(keyword)
                )
                .orderBy(user.count().desc())
                .limit(limitSize)
                .offset(pageNumber * limitSize)
                .fetch();
    }
}
