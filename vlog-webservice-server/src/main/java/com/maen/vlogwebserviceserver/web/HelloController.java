package com.maen.vlogwebserviceserver.web;

import com.maen.vlogwebserviceserver.service.posts.HelloService;
import com.maen.vlogwebserviceserver.web.dto.HelloRequestDto;
import com.maen.vlogwebserviceserver.web.dto.HelloResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class HelloController {


    private final HelloService helloService;

    @GetMapping("api/hello/all")
    public List<HelloResponseDto> hello() {
        return helloService.findAll();
    }

    @GetMapping("api/hello/{id}")
    public HelloResponseDto hell(@PathVariable Long id) {
        return helloService.findById(id);
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
