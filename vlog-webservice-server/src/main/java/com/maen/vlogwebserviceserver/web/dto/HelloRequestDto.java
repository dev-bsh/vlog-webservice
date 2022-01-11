package com.maen.vlogwebserviceserver.web.dto;

import com.maen.vlogwebserviceserver.domain.Hello;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloRequestDto {
    private String name;

    public HelloRequestDto(String name) {
        this.name = name;
    }

    public Hello toEntity() {
        return new Hello(this.name);
    }
}
