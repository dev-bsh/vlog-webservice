package com.maen.vlogwebserviceserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsAllResponseDto {
    // 메인화면 초기 및 스크롤 List 반환  & 검색 List 반환
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
