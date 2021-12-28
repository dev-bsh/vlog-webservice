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
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시물저장_불러오기(){
        //given
        Long userId = 1L;
        String videoName = "영상 제목";
        String description = "게시물 설명";

        postsRepository.save(Posts.builder()
                .userId(userId)
                .videoName(videoName)
                .description(description)
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getUserId()).isEqualTo(userId);
        assertThat(posts.getVideoName()).isEqualTo(videoName);
        assertThat(posts.getDescription()).isEqualTo(description);
        assertThat(posts.getViews()).isEqualTo(0);
    }


}
