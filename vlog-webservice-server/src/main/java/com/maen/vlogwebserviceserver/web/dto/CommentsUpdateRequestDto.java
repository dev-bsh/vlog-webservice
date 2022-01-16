package com.maen.vlogwebserviceserver.web.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsUpdateRequestDto {
    private String content;
    public CommentsUpdateRequestDto(String content) {
        this.content = content;
    }
}
