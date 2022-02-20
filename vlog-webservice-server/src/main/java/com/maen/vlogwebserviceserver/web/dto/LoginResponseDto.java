package com.maen.vlogwebserviceserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private Long userId;
    private String name;
    private String email;
    private String picture;
    private String tokenType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public LoginResponseDto(Long userId, String name, String email, String picture, String tokenType, String accessToken, String refreshToken) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }



}
