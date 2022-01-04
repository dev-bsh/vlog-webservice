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
public class PostsTagsRepositoryTest {

    @Autowired
    PostsTagsRepository postsTagsRepository;

    @AfterEach
    public void cleanup() {
        postsTagsRepository.deleteAll();
    }

    @Test
    public void 태그목록저장_불러오기() {
        //given
        Long postsId = 1L;
        Long tagsId = 1L;

        postsTagsRepository.save(PostsTags.builder()
                .postsId(postsId)
                .tagsId(tagsId)
                .build());

        //when
        List<PostsTags> tagLists = postsTagsRepository.findAll();

        //then
        PostsTags tagList = tagLists.get(0);

        assertThat(tagList.getPostsId()).isEqualTo(postsId);
        assertThat(tagList.getTagsId()).isEqualTo(tagsId);

    }


}
