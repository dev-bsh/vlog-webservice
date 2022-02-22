package com.maen.vlogwebserviceserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String name;
    private String email;
    private String picture;
    private Long followsId;
    private int followerNumber;
    private int followingNumber;

    @Builder
    public UserResponseDto(Long userId, String name, String email, String picture, Long followsId, int followerNumber, int followingNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.followsId = followsId;
        this.followerNumber = followerNumber;
        this.followingNumber = followingNumber;
    }
}
