package com.maen.vlogwebserviceserver.web;


import com.maen.vlogwebserviceserver.domain.posts.*;
import com.maen.vlogwebserviceserver.domain.user.User;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import com.maen.vlogwebserviceserver.service.posts.PostsService;
import com.maen.vlogwebserviceserver.web.dto.PostsSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PostsApiControllerTest {

    @LocalServerPort
    String port;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private PostsTagsRepository postsTagsRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostsService postsService;


    @Autowired
    private MockMvc mvc;

    @AfterEach
    public void tearDown() {
        postsRepository.deleteAll();
        postsTagsRepository.deleteAll();
        tagsRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    public void Posts_등록된다() throws Exception {
        //given
        String description = "설명";
        String tags = "#여행#일상#운동";
        Long userId = 1L;
        MockMultipartFile multipartFile = new MockMultipartFile("video","test.mp4", "video/mp4",new FileInputStream("C:\\Users\\Bang\\Desktop\\test.mp4"));
        String url = "http://localhost:"+port+"/api/v1/posts";
        tagsRepository.save(Tags.builder()
                .content("여행")
                .build());

        //when
        mvc.perform(multipart(url)
                .file(multipartFile)
                .param("userId", String.valueOf(userId))
                .param("tags", tags)
                .param("description",description))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        List<Posts> postsList = postsRepository.findAll();
        List<PostsTags> postsTagsList = postsTagsRepository.findAll();
        List<Tags> tagsList = tagsRepository.findAll();
        Posts posts = postsList.get(0);

        for(int i = 0; i < postsTagsList.size(); i++){
            PostsTags postsTags = postsTagsList.get(i);
            Tags tag = tagsList.get(i);
            assertThat(postsTags.getPostsId()).isEqualTo(posts.getId());
            assertThat(postsTags.getTagsId()).isEqualTo(tag.getId());
            System.out.print(tag.getContent()+" : ");
            System.out.println(tag.getCount());
        }

        assertThat(posts.getUserId()).isEqualTo(userId);
        assertThat(posts.getDescription()).isEqualTo(description);
        System.out.println("########## "+posts.getVideoName());

    }

    @Test
    public void Posts_불러온다() throws Exception {
        //given
        String description = "설명";
        String tags = "#여행#일상#운동";
        Long userId = 1L;
        MockMultipartFile multipartFile = new MockMultipartFile("video","test.mp4", "video/mp4",new FileInputStream("C:\\Users\\Bang\\Desktop\\test.mp4"));

        Long postsId = postsService.save(PostsSaveRequestDto.builder()
                .tags(tags)
                .userId(userId)
                .description(description)
                .video(multipartFile)
                .build());

        String name = "테스터";
        String email = "abc@123.com";
        String picture = "사진";

        userRepository.save(User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .build());

        String url = "http://localhost:8080/api/v1/posts/"+postsId;

        //then
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
