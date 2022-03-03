package com.maen.vlogwebserviceserver.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    private int count;

    @Builder
    public Tags(String content) {
        this.content = content;
        this.count = 1;
    }

    public String getHashTagContent() {
        return "#"+content;
    }

    public void countUp() {
        this.count++;
    }

    public void countDown() {
        this.count--;
    }

}
