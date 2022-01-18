package com.maen.vlogwebserviceserver.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String videoName;

    @Column(nullable = false)
    private String thumbnailName;

    private String description;

    private int views;


    @Builder
    public Posts(Long userId, String videoName, String description, String thumbnailName){
        this.userId = userId;
        this.videoName = videoName;
        this.description = description;
        this.thumbnailName = thumbnailName;
        this.views = 0;
    }

    public void upViews() {
        this.views++;
    }


}
