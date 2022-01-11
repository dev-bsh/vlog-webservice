package com.maen.vlogwebserviceserver.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class HelloResponseDto {
    private  String name;

    public HelloResponseDto(String name) {
        this.name = name;
    }

}
