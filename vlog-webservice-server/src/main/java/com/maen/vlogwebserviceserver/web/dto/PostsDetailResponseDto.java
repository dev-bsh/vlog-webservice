package com.maen.vlogwebserviceserver.web.dto;

import com.maen.vlogwebserviceserver.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostsResponseDto {

    private String author;
    private String description;
    private String tags;
    private String videoName;
    private int views;
    private int postsLike;
    private int commentsCount;


    @Builder
    public PostsResponseDto(Posts posts, String author, String tags, int postsLike, int commentsCount) {
        this.description = posts.getDescription();
        this.views = posts.getViews();
        this.videoName = posts.getVideoName();
        this.author = author;
        this.tags = tags;
        this.postsLike = postsLike;
        this.commentsCount = commentsCount;
    }

}
