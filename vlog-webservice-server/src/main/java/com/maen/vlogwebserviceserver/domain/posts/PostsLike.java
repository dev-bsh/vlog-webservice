package com.maen.vlogwebserviceserver.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PostsLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long postsId;


    @Builder
    public PostsLike(Long userId, Long postsId){
        this.userId = userId;
        this.postsId = postsId;
    }


}
