package com.maen.vlogwebserviceserver.web;


import com.maen.vlogwebserviceserver.service.posts.PostsService;
import com.maen.vlogwebserviceserver.web.dto.PostsResponseDto;
import com.maen.vlogwebserviceserver.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;


    @PostMapping("api/v1/posts")
    public Long save(@ModelAttribute PostsSaveRequestDto postsSaveRequestDto) throws IOException {
       return postsService.save(postsSaveRequestDto);
    }

    @GetMapping("api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) throws IOException{
        return postsService.findById(id);
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
