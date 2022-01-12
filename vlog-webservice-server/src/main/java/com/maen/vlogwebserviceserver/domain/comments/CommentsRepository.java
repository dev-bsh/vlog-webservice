package com.maen.vlogwebserviceserver.domain.comments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByPostsId(Long postsId);
}
