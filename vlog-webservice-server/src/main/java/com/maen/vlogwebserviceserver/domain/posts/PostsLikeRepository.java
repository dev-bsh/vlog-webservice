package com.maen.vlogwebserviceserver.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsLikeRepository extends JpaRepository<PostsLike, Long> {
    int countByPostsId(Long postsId);
    void deleteByPostsIdAndUserId(Long postsId, Long userId);
    List<PostsLike> findAllByUserId(Long userId);
}
