package com.maen.vlogwebserviceserver.service.posts;

import com.maen.vlogwebserviceserver.domain.posts.PostsLikeRepository;
import com.maen.vlogwebserviceserver.web.dto.PostsLikeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PostsLikeService {

    public final PostsLikeRepository postsLikeRepository;

    @Transactional
    public Long save(PostsLikeSaveRequestDto postsLikeSaveRequestDto) {
        return postsLikeRepository.save(postsLikeSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public void delete(Long postsId, Long userId) {
        postsLikeRepository.deleteByPostsIdAndUserId(postsId,userId);
    }

}
