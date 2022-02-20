package com.maen.vlogwebserviceserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowsCountResponseDto {
    private int follower;
    private int following;

    @Builder
    public FollowsCountResponseDto(int follower, int following) {
        this.follower = follower;
        this.following = following;
    }
}
