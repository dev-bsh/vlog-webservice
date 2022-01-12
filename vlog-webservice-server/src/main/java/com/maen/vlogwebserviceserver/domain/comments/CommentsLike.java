package com.maen.vlogwebserviceserver.domain.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class CommentsLike {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long commentsId;

    @Builder
    public CommentsLike(Long userId, Long commentsId) {
        this.userId = userId;
        this.commentsId = commentsId;
    }

}
