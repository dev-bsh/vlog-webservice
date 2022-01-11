package com.maen.vlogwebserviceserver.service.posts;

import com.maen.vlogwebserviceserver.domain.Hello;
import com.maen.vlogwebserviceserver.domain.HelloRepository;
import com.maen.vlogwebserviceserver.web.dto.HelloRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class HelloService {

    private final HelloRepository helloRepository;

    public void save(HelloRequestDto helloRequestDto) {
        helloRepository.save(helloRequestDto.toEntity());
    }

    public void update(Long id, HelloRequestDto helloRequestDto) {
        Hello hello = helloRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        hello.update(helloRequestDto.getName());
    }

    public void delete(Long id) {
        Hello hello = helloRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        helloRepository.delete(hello);
    }

}
