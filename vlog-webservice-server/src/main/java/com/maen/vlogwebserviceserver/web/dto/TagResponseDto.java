package com.maen.vlogwebserviceserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagResponseDto {
    private Long tagId;
    private String content;

    @Builder
    public TagResponseDto(Long tagId, String content) {
        this.tagId = tagId;
        this.content = content;
    }
}
