package com.maen.vlogwebserviceserver.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maen.vlogwebserviceserver.domain.comments.Comments;
import com.maen.vlogwebserviceserver.domain.comments.CommentsLikeRepository;
import com.maen.vlogwebserviceserver.domain.comments.CommentsRepository;
import com.maen.vlogwebserviceserver.web.dto.CommentsSaveRequestDto;
import com.maen.vlogwebserviceserver.web.dto.CommentsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CommentsApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private CommentsLikeRepository likeRepository;

    @AfterEach
    public void cleanup() {
        commentsRepository.deleteAll();
        likeRepository.deleteAll();
    }

    @Test
    public void comments_등록된다() throws Exception {
        //given
        Long userId = 1L;
        Long postsId = 1L;
        String content = "댓글 내용";

        CommentsSaveRequestDto saveRequestDto = CommentsSaveRequestDto.builder()
                .userId(userId)
                .postsId(postsId)
                .content(content)
                .build();

        String url = "http://localhost:8080/api/v1/comments";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        List<Comments> commentsList = commentsRepository.findAll();
        Comments comments = commentsList.get(0);

        assertThat(comments.getUserId()).isEqualTo(userId);
        assertThat(comments.getContent()).isEqualTo(content);
        assertThat(comments.getPostsId()).isEqualTo(postsId);
    }

    @Test
    public void comments_수정된다() throws Exception {
        //given
        Long userId = 1L;
        Long postsId = 1L;
        String content = "수정 전 댓글";
        String updateContent= "수정 후 댓글";

        Long commentsId = commentsRepository.save(Comments.builder()
                .userId(userId)
                .postsId(postsId)
                .content(content)
                .build()).getId();

        CommentsUpdateRequestDto updateRequestDto = new CommentsUpdateRequestDto(updateContent);

        String url = "http://localhost:8080/api/v1/comments/"+commentsId;

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        List<Comments> commentsList = commentsRepository.findAll();
        Comments updatedComment = commentsList.get(0);

        assertThat(updatedComment.getUserId()).isEqualTo(userId);
        assertThat(updatedComment.getPostsId()).isEqualTo(postsId);
        assertThat(updatedComment.getContent()).isEqualTo(updateContent);
    }

    @Test
    public void comments_삭제된다() throws Exception {
        //given
        Long userId = 1L;
        Long postsId = 1L;
        String content = "댓글";

        Long commentsId = commentsRepository.save(Comments.builder()
                .userId(userId)
                .postsId(postsId)
                .content(content)
                .build()).getId();

        String url = "http://localhost:8080/api/v1/comments/"+commentsId;

        //when
        mvc.perform(delete(url))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        assertThat(commentsRepository.existsById(commentsId)).isEqualTo(false);
    }


}