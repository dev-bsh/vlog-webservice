package com.maen.vlogwebserviceserver.config.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String email;
    private String name;
    private String picture;

    @Builder
    public UserDto(Long userId, String email, String name, String picture) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.picture = picture;
    }
}
