package com.maen.vlogwebserviceserver.config.auth.filter;

import com.maen.vlogwebserviceserver.config.auth.TokenService;
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
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = ((HttpServletRequest)request).getHeader("Auth");
        
        if (token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getUid(token);
            Optional<User> user = userRepository.findByEmail(email);
            if(user.isPresent()){
                UserDto userDto = UserDto.builder()
                        .email(user.get().getEmail())
                        .name(user.get().getName())
                        .picture(user.get().getPicture())
                        .build();
                Authentication auth = getAuthentication(userDto);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }

    public Authentication getAuthentication(UserDto user) {
        return new UsernamePasswordAuthenticationToken(user, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
