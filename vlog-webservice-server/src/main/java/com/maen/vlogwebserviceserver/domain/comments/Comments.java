package com.maen.vlogwebserviceserver.domain.comments;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long postsId;

    @Column(nullable = false)
    private String content;

    @Builder
    public Comments(Long userId, Long postsId, String content) {
        this.userId = userId;
        this.postsId = postsId;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }


}
