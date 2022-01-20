package com.maen.vlogwebserviceserver.web.dto;


import com.maen.vlogwebserviceserver.domain.comments.CommentsLike;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsLikeSaveRequestDto {

    private Long commentsId;
    private Long userId;

    @Builder
    public CommentsLikeSaveRequestDto(Long commentsId, Long userId) {
        this.commentsId = commentsId;
        this.userId = userId;
    }

    public CommentsLike toEntity() {
        return CommentsLike.builder()
                .userId(this.userId)
                .commentsId(this.commentsId)
                .build();
    }

}
