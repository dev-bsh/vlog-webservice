package com.maen.vlogwebserviceserver.web.dto;


import com.maen.vlogwebserviceserver.domain.posts.PostsLike;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsLikeSaveRequestDto {

    private Long postsId;
    private Long userId;

    @Builder
    public PostsLikeSaveRequestDto(Long postsId, Long userId) {
        this.postsId = postsId;
        this.userId = userId;
    }

    public PostsLike toEntity() {
        return PostsLike.builder()
                .postsId(this.postsId)
                .userId(this.userId)
                .build();
    }
}
