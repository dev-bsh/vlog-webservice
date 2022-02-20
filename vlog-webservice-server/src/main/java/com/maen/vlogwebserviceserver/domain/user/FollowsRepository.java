package com.maen.vlogwebserviceserver.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowsRepository extends JpaRepository<Follows, Long> {
    List<Long> findAllByUserId(Long userId);
    List<Long> findAllByFollowTargetId(Long followTargetId);
    void deleteByUserIdAndFollowTargetId(Long userId, Long followTargetId);
    int countByUserId(Long userId);
    int countByFollowTargetId(Long followTargetId);
}
