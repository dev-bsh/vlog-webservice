package com.maen.vlogwebserviceserver.web;


import com.maen.vlogwebserviceserver.service.comments.CommentsLikeService;
import com.maen.vlogwebserviceserver.service.posts.PostsLikeService;
import com.maen.vlogwebserviceserver.web.dto.CommentsLikeSaveRequestDto;
import com.maen.vlogwebserviceserver.web.dto.PostsLikeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LikeApiController {

    private final PostsLikeService postsLikeService;
    private final CommentsLikeService commentsLikeService;

    @PostMapping("api/v1/post-like")
    public int postsLikeUp(@RequestBody PostsLikeSaveRequestDto postsLikeSaveRequestDto) {
        return postsLikeService.save(postsLikeSaveRequestDto);
    }

    @DeleteMapping("api/v1/post-like/{postsId}/{userId}")
    public void postsLikeCancel(@PathVariable Long postsId, @PathVariable Long userId) {
        postsLikeService.delete(postsId,userId);
    }

    @PostMapping("api/v1/comment-like")
    public int commentsLikeUp(@RequestBody CommentsLikeSaveRequestDto commentsLikeSaveRequestDto) {
        return commentsLikeService.save(commentsLikeSaveRequestDto);
    }

    @DeleteMapping("api/v1/comment-like/{commentsId}/{userId}")
    public void commentsLikeCancel(@PathVariable Long commentsId, @PathVariable Long userId) {
        commentsLikeService.delete(commentsId,userId);
    }

}
