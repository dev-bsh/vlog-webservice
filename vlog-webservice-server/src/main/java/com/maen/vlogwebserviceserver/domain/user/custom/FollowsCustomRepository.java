package com.maen.vlogwebserviceserver.domain.user.custom;

import com.maen.vlogwebserviceserver.domain.user.Follows;

import java.util.List;

public interface FollowsCustomRepository {
    List<Follows> findFollowerList(Long userId, Long lastFollowerId);
    List<Follows> findFollowingList(Long userId, Long lastFollowingId);
}
