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
public class PostsCustomRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private PostsTagsRepository postsTagsRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @AfterEach
    public void cleanup() {
        postsTagsRepository.deleteAll();
        postsRepository.deleteAll();
        tagsRepository.deleteAll();
    }


    @Test
    public void 태그와일치하는_첫_게시물목록_불러온다() {
        //given
        String tag = "운동";
        String[] tags = {"운동","여행"};

        for(int i = 1; i<=12; i++) {
            Long postsId = postsRepository.save(Posts.builder()
                    .userId((long) i)
                    .description(String.valueOf(i))
                    .thumbnailName(String.valueOf(i))
                    .videoName(String.valueOf(i))
                    .build()).getId();
            if(i-1<tags.length){
            tagsRepository.save(Tags.builder()
                        .content(tags[i-1])
                        .build());

            }
            // 짝 수 게시물마다 운동 태그가 들어감
            postsTagsRepository.save(PostsTags.builder()
                    .tagsId((long) (i%2 + 1))
                    .postsId(postsId)
                    .build());
        }

        //when
        List<Posts> postsLists = postsRepository.findAllByTag(tag,null);

        //then
        assertThat(postsLists.size()).isEqualTo(6);
        for(Posts posts : postsLists) {
            // 게시물 ID가 짝 수 인지 체크
            assertThat(posts.getId()%2).isEqualTo(0);
        }
    }


}
