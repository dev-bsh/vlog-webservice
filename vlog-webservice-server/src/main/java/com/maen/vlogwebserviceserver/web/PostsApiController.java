package com.maen.vlogwebserviceserver.web;


import com.maen.vlogwebserviceserver.service.posts.MediaService;
import com.maen.vlogwebserviceserver.service.posts.PostsService;
import com.maen.vlogwebserviceserver.web.dto.PostsAllResponseDto;
import com.maen.vlogwebserviceserver.web.dto.PostsDetailResponseDto;
import com.maen.vlogwebserviceserver.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.JCodecException;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;
    private final MediaService mediaService;

    @PostMapping("api/v1/posts")
    public Long save(@ModelAttribute PostsSaveRequestDto postsSaveRequestDto) throws IOException, JCodecException {
       return postsService.save(postsSaveRequestDto);
    }

    @GetMapping("api/v1/posts/{last_post_id}")
    public List<PostsAllResponseDto> findAll(@PathVariable(required = false) Long last_post_id) {
        return postsService.findAll(last_post_id);
    }

    @GetMapping("api/v1/posts/{id}/detail")
    public PostsDetailResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @GetMapping("api/v1/posts/video/{videoName}")
    public ResponseEntity<ResourceRegion> findVideoByName(@RequestHeader HttpHeaders httpHeaders, @PathVariable String videoName) throws Exception{
        return mediaService.findVideoByName(httpHeaders,videoName);
    }

    @GetMapping(value = "api/v1/posts/thumbnail/{thumbnailName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> findThumbnailByName(@PathVariable String thumbnailName) throws IOException {
        return mediaService.findThumbnailByName(thumbnailName);
    }




//    @PutMapping("api/v1/posts/{id}")
//    public Long update() {
//
//    }
//
//
//    @DeleteMapping("api/v1/posts/{id}")
//    public Long delete() {
//
//    }




}
