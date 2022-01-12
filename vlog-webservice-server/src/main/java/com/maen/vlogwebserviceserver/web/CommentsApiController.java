package com.maen.vlogwebserviceserver.web;


import com.maen.vlogwebserviceserver.service.comments.CommentsService;
import com.maen.vlogwebserviceserver.web.dto.CommentsResponseDto;
import com.maen.vlogwebserviceserver.web.dto.CommentsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentsApiController {

    private final CommentsService commentsService;

    @PostMapping("api/v1/comments")
    public Long save(@RequestBody CommentsSaveRequestDto saveRequestDto) {
        return commentsService.save(saveRequestDto);
    }

    @GetMapping("api/v1/comments/{postsId}")
    public List<CommentsResponseDto> findAllByPostsId(@PathVariable Long postsId) {
        return commentsService.findAllByPostsId(postsId);
    }

    @PutMapping("api/v1/comments/{id}")
    public Long update(@PathVariable Long id ,@RequestBody String content) {
        return commentsService.update(id, content);
    }

    @DeleteMapping("api/v1/comments/{id}")
    public Long delete(@PathVariable Long id) {
        commentsService.delete(id);
        return id;
    }
}
