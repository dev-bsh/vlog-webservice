package com.maen.vlogwebserviceserver.web.dto;

import com.maen.vlogwebserviceserver.domain.posts.Posts;
import com.maen.vlogwebserviceserver.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostsDetailResponseDto {

    private Long postsId;
    private Long authorId;
    private String authorName;
    private String description;
    private List<String> tags;
    private String videoName;
    private int views;
    private int postsLike;
    private int totalCommentsCount;


    @Builder
    public PostsDetailResponseDto(Posts posts, User user, List<String> tags, int postsLike, int totalCommentsCount) {
        this.postsId = posts.getId();
        this.description = posts.getDescription();
        this.views = posts.getViews();
        this.videoName = posts.getVideoName();
        this.authorId = user.getId();
        this.authorName = user.getName();
        this.tags = tags;
        this.postsLike = postsLike;
        this.totalCommentsCount = totalCommentsCount;
    }

}
