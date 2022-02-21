package com.maen.vlogwebserviceserver.web.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsAllResponseDto {
    private Long commentId;
    private String author;
    private String contents;
    private int commentsLike;

    @Builder
    public CommentsAllResponseDto(Long commentId, String author, String contents, int commentsLike) {
        this.commentId = commentId;
        this.author = author;
        this.contents = contents;
        this.commentsLike = commentsLike;
    }


}
