package com.maen.vlogwebserviceserver.web;


import com.maen.vlogwebserviceserver.service.user.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class LoginApiController {

    private final LoginService loginService;

    @GetMapping("/api/v1/login/{provider}")
    public String login(@PathVariable String provider) {
        StringBuilder uri = loginService.getAuthorizationCode(provider);
        return "redirect:"+uri;
    }

}
