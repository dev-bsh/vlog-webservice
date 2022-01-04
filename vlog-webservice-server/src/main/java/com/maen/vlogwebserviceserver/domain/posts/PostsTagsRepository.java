package com.maen.vlogwebserviceserver.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsTagsRepository extends JpaRepository<PostsTags, Long> {
    List<Long> findByPostsId(Long postsId);
}
