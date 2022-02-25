package com.maen.vlogwebserviceserver.config.auth.filter;

import com.maen.vlogwebserviceserver.config.auth.JwtService;
import com.maen.vlogwebserviceserver.config.auth.dto.UserDto;
import com.maen.vlogwebserviceserver.domain.user.User;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {
    private final JwtService jwtService;
    public final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = ((HttpServletRequest)request).getHeader("ACCESS_TOKEN");

        if(token != null && jwtService.verifyToken(token)) {
            String userId = jwtService.getPayload(token);
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다. id="+userId));
            Authentication authentication = getAuthentication(UserDto.builder()
                    .userId(user.getId())
                    .name(user.getName())
                    .picture(user.getPicture())
                    .email(user.getEmail())
                    .build());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    public Authentication getAuthentication(UserDto member) {
        return new UsernamePasswordAuthenticationToken(member, "",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
