package com.maen.vlogwebserviceserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsAllResponseDto {

    private Long postsId;
    private Long authorId;
    private String authorName;
    private int postsLike;
    private int views;
    private String thumbnailName;

    @Builder
    public PostsAllResponseDto(Long postsId, Long authorId, String authorName, int postsLike, int views, String thumbnailName) {
        this.postsId = postsId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.postsLike = postsLike;
        this.views = views;
        this.thumbnailName = thumbnailName;
    }


}
