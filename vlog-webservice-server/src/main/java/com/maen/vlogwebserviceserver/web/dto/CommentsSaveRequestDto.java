package com.maen.vlogwebserviceserver.web.dto;


import com.maen.vlogwebserviceserver.domain.comments.Comments;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsSaveRequestDto {

    private Long userId;
    private Long postsId;
    private String content;

    @Builder
    public CommentsSaveRequestDto(Long userId, Long postsId, String content) {
        this.userId = userId;
        this.postsId = postsId;
        this.content = content;
    }

    public Comments toEntity() {
        return Comments.builder()
                .userId(userId)
                .postsId(postsId)
                .content(content)
                .build();
    }

}
