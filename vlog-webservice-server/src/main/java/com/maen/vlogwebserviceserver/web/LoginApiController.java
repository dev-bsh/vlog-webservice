package com.maen.vlogwebserviceserver.web;


import com.maen.vlogwebserviceserver.service.user.LoginService;
import com.maen.vlogwebserviceserver.web.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class LoginApiController {

    private final LoginService loginService;

    @GetMapping("/api/v1/login/{provider}")
    public String login(@PathVariable String provider) {
        StringBuilder uri = loginService.getAuthorizationCode(provider);
        return "redirect:"+uri;
    }

    @GetMapping("/api/v1/jwt/{provider}")
    public ResponseEntity<LoginResponseDto> getToken(@PathVariable String provider, @RequestParam String code) {
        LoginResponseDto responseDto = loginService.login(provider, code);
        return ResponseEntity.ok().body(responseDto);
    }

}
