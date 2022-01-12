package com.maen.vlogwebserviceserver.domain.comments;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsLikeRepository extends JpaRepository<CommentsLike, Long> {
    int countByCommentsId(Long commentsId);
}
