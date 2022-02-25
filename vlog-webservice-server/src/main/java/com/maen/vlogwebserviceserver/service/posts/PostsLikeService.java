package com.maen.vlogwebserviceserver.service.posts;

import com.maen.vlogwebserviceserver.domain.posts.PostsLike;
import com.maen.vlogwebserviceserver.domain.posts.PostsLikeRepository;
import com.maen.vlogwebserviceserver.web.dto.PostsLikeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PostsLikeService {

    private final PostsLikeRepository postsLikeRepository;

    @Transactional
    public int save(PostsLikeSaveRequestDto postsLikeSaveRequestDto) {
        postsLikeRepository.save(postsLikeSaveRequestDto.toEntity());
        return postsLikeRepository.countByPostsId(postsLikeSaveRequestDto.getPostsId());
    }

    @Transactional
    public void delete(Long postsId, Long userId) {
        postsLikeRepository.deleteByPostsIdAndUserId(postsId,userId);
    }

    @Transactional(readOnly = true)
    public List<Long> findAllPostsIdByUserId(Long userId) {
        List<PostsLike> postsLikeList = postsLikeRepository.findAllByUserId(userId);
        List<Long> postIds = new ArrayList<>();
        for(PostsLike like : postsLikeList) {
            postIds.add(like.getPostsId());
        }
        return postIds;
    }

    public void deleteByUserId(Long userId) {
        postsLikeRepository.deleteByUserId(userId);
    }
}
