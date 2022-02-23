package com.maen.vlogwebserviceserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class PostsUpdateRequestDto {

    private String description;
    private String tags;
    private MultipartFile video;

    private String videoName;
    private String thumbnailName;

    @Builder
    public PostsUpdateRequestDto(String description, String tags, MultipartFile video){
        this.description = description;
        this.tags = tags;
        this.video = video;
    }

}
