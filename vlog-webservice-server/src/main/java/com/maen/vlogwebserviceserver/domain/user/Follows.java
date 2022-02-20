package com.maen.vlogwebserviceserver.domain.user;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Follows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long followTargetId;

    @Builder
    public Follows(Long userId, Long followTargetId) {
        this.userId = userId;
        this.followTargetId = followTargetId;
    }

}
