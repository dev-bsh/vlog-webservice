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

    private String description;

    private int views;


    @Builder
    public Posts(Long userId, String videoName, String description){
        this.userId = userId;
        this.videoName = videoName;
        this.description = description;
        this.views = 0;
    }

    public void upViews() {
        this.views++;
    }


}
