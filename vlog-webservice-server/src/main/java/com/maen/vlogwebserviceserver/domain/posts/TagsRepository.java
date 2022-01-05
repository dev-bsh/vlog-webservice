package com.maen.vlogwebserviceserver.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags, Long> {
    boolean existsByContent(String content);
    Tags findByContent(String content);
}
