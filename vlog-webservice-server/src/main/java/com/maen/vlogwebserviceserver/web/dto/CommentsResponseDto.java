package com.maen.vlogwebserviceserver.web.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsResponseDto {

    private String author;
    private String contents;
    private int commentsLike;

    @Builder
    public CommentsResponseDto(String author, String contents, int commentsLike) {
        this.author = author;
        this.contents = contents;
        this.commentsLike = commentsLike;
    }


}
