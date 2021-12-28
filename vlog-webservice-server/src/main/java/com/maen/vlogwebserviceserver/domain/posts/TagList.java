package com.maen.vlogwebserviceserver.domain.posts;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class TagList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postsId;

    @Column(nullable = false)
    private Long tagsId;


    @Builder
    public TagList(Long postsId, Long tagsId){
        this.postsId = postsId;
        this.tagsId = tagsId;
    }



}
