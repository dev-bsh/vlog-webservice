package com.maen.vlogwebserviceserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsAllResponseDto {

    private String author;
    private int postsLike;
    private int views;
    private String thumbnailName;

    @Builder
    public PostsAllResponseDto(String author, int postsLike, int views, String thumbnailName) {
        this.author = author;
        this.postsLike = postsLike;
        this.views = views;
        this.thumbnailName = thumbnailName;
    }


}
