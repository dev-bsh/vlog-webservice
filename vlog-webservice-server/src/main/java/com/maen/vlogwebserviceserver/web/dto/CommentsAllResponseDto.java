package com.maen.vlogwebserviceserver.web.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsAllResponseDto {

    private String author;
    private String contents;
    private int commentsLike;

    @Builder
    public CommentsAllResponseDto(String author, String contents, int commentsLike) {
        this.author = author;
        this.contents = contents;
        this.commentsLike = commentsLike;
    }


}
