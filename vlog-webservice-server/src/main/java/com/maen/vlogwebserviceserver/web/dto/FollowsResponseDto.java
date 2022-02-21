package com.maen.vlogwebserviceserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowsResponseDto {
    private Long userId;
    private String name;
    private String email;
    private String picture;
    private int followerNumber;
    private int followingNumber;

    @Builder
    public FollowsResponseDto(Long userId, String name, String email, String picture, int followerNumber, int followingNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.followerNumber = followerNumber;
        this.followingNumber = followingNumber;
    }
}
