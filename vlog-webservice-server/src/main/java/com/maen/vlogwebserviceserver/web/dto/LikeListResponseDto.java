package com.maen.vlogwebserviceserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LikeListResponseDto {
    private List<Long> userLikePostIds;
    private List<Long> userLikeCommentIds;

    @Builder
    public LikeListResponseDto(List<Long> userLikePostIds, List<Long> userLikeCommentIds) {
        this.userLikePostIds = userLikePostIds;
        this.userLikeCommentIds = userLikeCommentIds;
    }
}
