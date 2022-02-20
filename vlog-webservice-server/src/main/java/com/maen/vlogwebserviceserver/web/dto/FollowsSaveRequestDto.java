package com.maen.vlogwebserviceserver.web.dto;

import com.maen.vlogwebserviceserver.domain.user.Follows;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowsSaveRequestDto {
    private Long userId;
    private Long followTargetId;

    @Builder
    public FollowsSaveRequestDto(Long userId, Long followTargetId) {
        this.userId = userId;
        this.followTargetId = followTargetId;
    }

    public Follows toEntity() {
        return Follows.builder()
                .userId(userId)
                .followTargetId(followTargetId)
                .build();
    }
}
