package com.maen.vlogwebserviceserver.web.dto;

import com.maen.vlogwebserviceserver.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private Long userId;
    private String description;
    private String tags;
    private MultipartFile video;

    @Builder
    public PostsSaveRequestDto(Long userId, String description, String tags, MultipartFile video){
        this.userId = userId;
        this.description = description;
        this.tags = tags;
        this.video = video;
    }

    public Posts toEntity(String videoName) {
        return Posts.builder()
                .userId(userId)
                .description(description)
                .videoName(videoName)
                .build();
    }

}
