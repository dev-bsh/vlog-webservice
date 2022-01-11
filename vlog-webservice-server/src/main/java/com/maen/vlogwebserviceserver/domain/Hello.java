package com.maen.vlogwebserviceserver.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Hello {

    @Id
    private Long id;

    private String name;

    public Hello(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }

}
