package com.maen.vlogwebserviceserver.domain.posts.custom;

import com.maen.vlogwebserviceserver.domain.posts.Posts;

import java.util.List;

public interface PostsCustomRepository {
    List<Posts> findRecentListInMainPage(Long lastPostsId);
    List<Posts> findRecentListByTagSearch(String tag, Long lastPostsId);
    List<Posts> findPopularListInMainPage(Integer pageNumber);
    List<Posts> findPopularListByTagSearch(String tag, Integer pageNumber);
}
