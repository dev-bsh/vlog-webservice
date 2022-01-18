package com.maen.vlogwebserviceserver.domain.posts;

import com.maen.vlogwebserviceserver.domain.posts.custom.PostsCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long>, PostsCustomRepository {
}
