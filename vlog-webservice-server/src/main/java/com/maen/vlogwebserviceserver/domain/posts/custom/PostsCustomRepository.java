package com.maen.vlogwebserviceserver.domain.posts.custom;

import com.maen.vlogwebserviceserver.domain.posts.Posts;

import java.util.List;

public interface PostsCustomRepository {

    List<Posts> findAllInMainPage(Long lastPostId);
    List<Posts> findAllByTag(String tag, Long lastPostId);

}
