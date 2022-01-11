package com.maen.vlogwebserviceserver.web.dto;

import com.maen.vlogwebserviceserver.domain.Hello;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloRequestDto {
    private final String name;


    public Hello toEntity() {
        return new Hello(name);
    }
}
