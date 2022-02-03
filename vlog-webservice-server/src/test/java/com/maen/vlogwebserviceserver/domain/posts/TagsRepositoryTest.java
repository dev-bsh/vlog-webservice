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
public class TagsRepositoryTest {

    @Autowired
    TagsRepository tagsRepository;

    @AfterEach
    public void cleanup() {
        tagsRepository.deleteAll();
    }

    @Test
    public void 태그저장_불러오기() {
        //given
        String content = "운동";
        tagsRepository.save(Tags.builder()
                .content(content)
                .build());

        //when
        List<Tags> tagsList = tagsRepository.findAll();

        //then
        Tags tags = tagsList.get(0);
        assertThat(tags.getContent()).isEqualTo("#"+content);
    }

}
