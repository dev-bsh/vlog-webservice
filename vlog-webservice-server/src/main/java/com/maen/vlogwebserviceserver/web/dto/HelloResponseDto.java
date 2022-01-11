package com.maen.vlogwebserviceserver.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HelloResponseDto {
    private String name;

    public HelloResponseDto(String name) {
        this.name = name;
    }

}
