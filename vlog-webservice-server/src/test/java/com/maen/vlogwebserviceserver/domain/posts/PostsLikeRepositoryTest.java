package com.maen.vlogwebserviceserver.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsLikeRepositoryTest {

    @Autowired
    PostsLikeRepository postsLikeRepository;

    @AfterEach
    public void cleanup() {
        postsLikeRepository.deleteAll();
    }

    @Test
    public void 좋아요저장_불러오기() {
        //given
        Long userId = 1L;
        Long postsId = 1L;

        postsLikeRepository.save(PostsLike.builder()
                .userId(userId)
                .postsId(postsId)
                .build());

        //when
        List<PostsLike> postsLikeList = postsLikeRepository.findAll();

        //then
        PostsLike postsLike = postsLikeList.get(0);
        assertThat(postsLike.getUserId()).isEqualTo(userId);
        assertThat(postsLike.getPostsId()).isEqualTo(postsId);

    }




}
