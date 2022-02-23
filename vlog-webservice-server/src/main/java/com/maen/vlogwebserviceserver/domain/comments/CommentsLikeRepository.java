package com.maen.vlogwebserviceserver.domain.comments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsLikeRepository extends JpaRepository<CommentsLike, Long> {
    int countByCommentsId(Long commentsId);
    void deleteByCommentsIdAndUserId(Long commentsId, Long userId);
    List<CommentsLike> findAllByUserId(Long userId);
    List<CommentsLike> findAllByCommentsId(Long commentsId);
    void deleteAllByCommentsId(Long commentsId);
}
