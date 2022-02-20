package com.maen.vlogwebserviceserver.domain.posts.custom;

import com.maen.vlogwebserviceserver.domain.posts.Posts;

import java.util.List;

public interface PostsCustomRepository {

    List<Posts> findListInMainPage(Long lastPostId, String orderType);
    List<Posts> findListByTagSearch(String tag, Long lastPostId, String orderType);


}
