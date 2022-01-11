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
        Long user1Id = 1L;
        Long posts1Id = 1L;

        Long user2Id = 2L;
        Long posts2Id = 2L;

        postsLikeRepository.save(PostsLike.builder()
                .userId(user1Id)
                .postsId(posts1Id)
                .build());
        postsLikeRepository.save(PostsLike.builder()
                .userId(user1Id)
                .postsId(posts2Id)
                .build());
        postsLikeRepository.save(PostsLike.builder()
                .userId(user2Id)
                .postsId(posts2Id)
                .build());

        //when
        int post1LikeCount = postsLikeRepository.countByPostsId(posts1Id);
        int post2LikeCount = postsLikeRepository.countByPostsId(posts2Id);

        //then
        assertThat(post1LikeCount).isEqualTo(1);
        assertThat(post2LikeCount).isEqualTo(2);
    }




}
