package com.maen.vlogwebserviceserver.domain.comments.custom;

import com.maen.vlogwebserviceserver.domain.comments.Comments;

import java.util.List;

public interface CommentsCustomRepository {
    List<Comments> findAllInPostsDetail(Long postsId, Long commentsId);
}
