package com.maen.vlogwebserviceserver.web;

import com.maen.vlogwebserviceserver.service.posts.HelloService;
import com.maen.vlogwebserviceserver.web.dto.HelloRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class HelloController {


    private final HelloService helloService;

    @GetMapping("api/hello")
    public List<String> hell() {
        return Arrays.asList("안녕하세요", "Hello");
    }

    @PostMapping("api/hello")
    public void helloPost(@RequestBody HelloRequestDto helloRequestDto) {
        helloService.save(helloRequestDto);
    }

    @PutMapping("api/hello/{id}")
    public void helloPut(@PathVariable Long id, @RequestBody HelloRequestDto helloRequestDto) {
        helloService.update(id, helloRequestDto);
    }

    @DeleteMapping("api/hello/{id}")
    public void helloDel(@PathVariable Long id) {
        helloService.delete(id);
    }

}
