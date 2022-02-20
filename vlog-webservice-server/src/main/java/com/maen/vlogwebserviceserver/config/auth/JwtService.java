package com.maen.vlogwebserviceserver.config.auth;

import com.maen.vlogwebserviceserver.config.auth.dto.Jwt;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    // 인증토큰 = 30분, 리프레쉬 토큰 = 30일
    public Jwt generateToken(String payload) {
        long tokenPeriod = 1000L * 60L * 30L;
        long refreshPeriod = 1000L * 60L * 60L * 24L * 30L;

        Claims claims = Jwts.claims().setSubject(payload);

        Date now = new Date();
        return new Jwt(
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

    public String getPayload(String token) {
        try{
            return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (JwtException e) {
            throw new RuntimeException("유효하지 않은 토큰");
        }
    }

}
