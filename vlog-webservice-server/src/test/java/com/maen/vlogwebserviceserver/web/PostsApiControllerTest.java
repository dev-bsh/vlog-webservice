package com.maen.vlogwebserviceserver.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maen.vlogwebserviceserver.domain.comments.Comments;
import com.maen.vlogwebserviceserver.domain.comments.CommentsRepository;
import com.maen.vlogwebserviceserver.domain.posts.*;
import com.maen.vlogwebserviceserver.domain.user.User;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import com.maen.vlogwebserviceserver.service.comments.CommentsService;
import com.maen.vlogwebserviceserver.service.posts.PostsService;
import com.maen.vlogwebserviceserver.web.dto.CommentsResponseDto;
import com.maen.vlogwebserviceserver.web.dto.PostsAllResponseDto;
import com.maen.vlogwebserviceserver.web.dto.PostsSaveRequestDto;
import org.jcodec.api.JCodecException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostsApiControllerTest {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private PostsTagsRepository postsTagsRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private PostsService postsService;


    @Autowired
    private MockMvc mvc;



    @AfterEach
    public void tearDown() {
        commentsRepository.deleteAll();
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
        String url = "http://localhost:8080/api/v1/posts";
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
        System.out.println("########## "+posts.getThumbnailName());

    }

    @Test
    public void Posts_detail_불러온다() throws Exception {
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

        String url = "http://localhost:8080/api/v1/posts/"+postsId+"/detail";

        //then
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void main에서_첫posts목록_불러온다() throws Exception {
        //given
        String description = "설명";
        String tags = "#여행#일상#운동";
        String userName = "테스터";
        String userPic = "프로필";
        String email = "이메일";
        MockMultipartFile multipartFile = new MockMultipartFile("video","test.mp4", "video/mp4",new FileInputStream("C:\\Users\\Bang\\Desktop\\test.mp4"));
        List<PostsAllResponseDto> responseDtoList = new ArrayList<>();
        int lastSavedPostsId = 16;
        int postsListSize = 12;

        for(int i = 1 ; i<lastSavedPostsId; i++) {
            Long userId = userRepository.save(User.builder()
                    .name(userName)
                    .email(email)
                    .picture(userPic)
                    .build()).getId();
            Long postsId = postsService.save(PostsSaveRequestDto.builder()
                    .userId(userId)
                    .description(description)
                    .tags(tags)
                    .video(multipartFile)
                    .build());
            if(i>=lastSavedPostsId-postsListSize) {
                Posts posts = postsRepository.findById(postsId).orElseThrow(()->new IllegalArgumentException("없는 posts"));
                responseDtoList.add(PostsAllResponseDto.builder()
                        .author(userName)
                        .views(posts.getViews())
                        .thumbnailName(posts.getThumbnailName())
                        .postsLike(0)
                        .build());
            }
        }

        String url = "http://localhost:8080/api/v1/posts";

        //when
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                //then
                .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDtoList)));
    }

    @Test
    public void main에서_다음새로운posts목록_불러온다() throws Exception{
        //given
        String description = "설명";
        String tags = "#여행#일상#운동";
        String userName = "테스터";
        String userPic = "프로필";
        String email = "이메일";
        MockMultipartFile multipartFile = new MockMultipartFile("video","test.mp4", "video/mp4",new FileInputStream("C:\\Users\\Bang\\Desktop\\test.mp4"));
        List<PostsAllResponseDto> responseDtoList = new ArrayList<>();
        int lastSavedPostsId = 13;
        int lastReadPostsId = 8;

        for(int i = 1 ; i<lastSavedPostsId; i++) {
            Long userId = userRepository.save(User.builder()
                    .name(userName)
                    .email(email)
                    .picture(userPic)
                    .build()).getId();
            Long postsId = postsService.save(PostsSaveRequestDto.builder()
                    .userId(userId)
                    .description(description)
                    .tags(tags)
                    .video(multipartFile)
                    .build());
            if(i<lastReadPostsId) {
                Posts posts = postsRepository.findById(postsId).orElseThrow(()->new IllegalArgumentException("없는 posts"));
                responseDtoList.add(PostsAllResponseDto.builder()
                        .author(userName)
                        .views(posts.getViews())
                        .thumbnailName(posts.getThumbnailName())
                        .postsLike(0)
                        .build());
            }
        }

        String url = "http://localhost:8080/api/v1/posts/"+lastReadPostsId;

        //when
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                //then
                .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDtoList)));
    }



    @Test
    public void posts_detail에서_첫comments목록_불러온다() throws Exception {
        // commentsId == null 이면 id 최근부터 최대 12개

        //given
        List<CommentsResponseDto> responseDtoList = new ArrayList<>();
        Long postsId = 1L;
        int lateSavedCommentsId = 20;
        int commentsListSize = 12;
        String userName = "테스터";
        String userPic = "픽쳐";
        String email = "이메일";

        for(int i = 1; i < lateSavedCommentsId; i++) {
            userRepository.save(User.builder()
                    .name(userName)
                    .picture(userPic)
                    .email(email)
                    .build());
            commentsRepository.save(Comments.builder()
                    .userId((long) i)
                    .postsId(postsId)
                    .content(String.valueOf(i))
                    .build());
            if(i>=lateSavedCommentsId-commentsListSize){
                responseDtoList.add(CommentsResponseDto.builder()
                        .author(userName)
                        .contents(String.valueOf(i))
                        .commentsLike(0)
                        .build());
            }
        }

        String url = "http://localhost:8080/api/v1/posts/"+postsId+"/comments";

        //when
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                //then
                .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDtoList)));
    }

    @Test
    public void posts_detail에서_다음새로운comments목록_불러온다() throws Exception {
        // commentsId == n 이면 n+1 부터 최대 12개

        //given
        List<CommentsResponseDto> responseDtoList = new ArrayList<>();
        Long postsId = 1L;
        int lateSavedCommentsId = 20;
        int lastReadCommentsId = 8;
        String userName = "테스터";
        String userPic = "픽쳐";
        String email = "이메일";

        for(int i = 1; i < lateSavedCommentsId; i++) {
            userRepository.save(User.builder()
                    .name(userName)
                    .picture(userPic)
                    .email(email)
                    .build());
            commentsRepository.save(Comments.builder()
                    .userId((long) i)
                    .postsId(postsId)
                    .content(String.valueOf(i))
                    .build());
            if(i<lastReadCommentsId){
                responseDtoList.add(CommentsResponseDto.builder()
                        .author(userName)
                        .contents(String.valueOf(i))
                        .commentsLike(0)
                        .build());
            }
        }

        String url = "http://localhost:8080/api/v1/posts/"+postsId+"/comments/"+lastReadCommentsId;

        //when
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                //then
                .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDtoList)));


    }


    @Test
    public void Thumbnail_불러온다() {

    }

    @Test
    public void Video_불러온다() {

    }

}
