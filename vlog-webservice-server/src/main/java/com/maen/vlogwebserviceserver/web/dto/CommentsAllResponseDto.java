package com.maen.vlogwebserviceserver.web.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsAllResponseDto {
    private Long commentId;
    private Long authorId;
    private String author;
    private String authorPicture;
    private String contents;
    private int commentsLike;

    @Builder
    public CommentsAllResponseDto(Long commentId, Long authorId, String author, String authorPicture, String contents, int commentsLike) {
        this.commentId = commentId;
        this.authorId = authorId;
        this.author = author;
        this.authorPicture = authorPicture;
        this.contents = contents;
        this.commentsLike = commentsLike;
    }


}
