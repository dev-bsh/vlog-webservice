package com.maen.vlogwebserviceserver.config.auth;

import com.maen.vlogwebserviceserver.config.auth.dto.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {
    private String secretKey = "token-secret-key";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // 인증토큰 = 30분, 리프레쉬 토큰 = 30일
    public Token generateToken(String uid, String role) {
        long tokenPeriod = 1000L * 60L * 30L;
        long refreshPeriod = 1000L * 60L * 60L * 24L * 30L;

        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("role", role);

        Date now = new Date();
        return new Token(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + tokenPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact()
        );
    }

    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claimsJws =Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return claimsJws.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUid(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
