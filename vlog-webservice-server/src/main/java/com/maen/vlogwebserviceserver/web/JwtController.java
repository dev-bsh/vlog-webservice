package com.maen.vlogwebserviceserver.web;

import com.maen.vlogwebserviceserver.config.auth.JwtService;
import com.maen.vlogwebserviceserver.config.auth.dto.Jwt;
import com.maen.vlogwebserviceserver.service.user.LoginService;
import com.maen.vlogwebserviceserver.web.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class JwtController {
    private final JwtService tokenService;
    private final LoginService loginService;

    @GetMapping("/api/v1/jwt/{provider}")
    public ResponseEntity<LoginResponseDto> getToken(@PathVariable String provider, @RequestParam String code) {
        LoginResponseDto responseDto = loginService.login(provider, code);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/api/v1/jwt/expired")
    public Boolean auth(HttpServletRequest request) {
        String token = request.getHeader("ACCESS_TOKEN");
        return tokenService.verifyToken(token);
    }

    @GetMapping("/api/v1/jwt/refresh")
    public ResponseEntity<String> refreshAuth(@RequestHeader("REFRESH_TOKEN") String refreshToken) {
        HttpHeaders response = new HttpHeaders();
        if (refreshToken != null && tokenService.verifyToken(refreshToken)) {
            String userId = tokenService.getPayload(refreshToken);
            Jwt newToken = tokenService.generateToken(userId);

            response.set("ACCESS_TOKEN", newToken.getAccessToken());
            response.set("REFRESH_TOKEN", newToken.getRefreshToken());
            response.setContentType(MediaType.APPLICATION_JSON);

            return ResponseEntity.ok()
                    .headers(response)
                    .body("Refresh_Success");
        }
        throw new RuntimeException();
    }
}
