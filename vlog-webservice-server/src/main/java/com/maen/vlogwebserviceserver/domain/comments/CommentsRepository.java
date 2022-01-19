package com.maen.vlogwebserviceserver.domain.comments;

import com.maen.vlogwebserviceserver.domain.comments.custom.CommentsCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long>, CommentsCustomRepository {
    int countByPostsId(Long postsId);
}
