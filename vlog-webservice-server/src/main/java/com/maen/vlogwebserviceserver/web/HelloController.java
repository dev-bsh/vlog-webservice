package com.maen.vlogwebserviceserver.web;

import com.maen.vlogwebserviceserver.service.HelloService;
import com.maen.vlogwebserviceserver.service.user.LoginService;
import com.maen.vlogwebserviceserver.web.dto.HelloRequestDto;
import com.maen.vlogwebserviceserver.web.dto.HelloResponseDto;
import com.maen.vlogwebserviceserver.web.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/test")
    public String index() {
        return "success";
    }

}
