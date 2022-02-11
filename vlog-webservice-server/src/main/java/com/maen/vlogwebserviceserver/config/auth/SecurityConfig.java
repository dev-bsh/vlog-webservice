package com.maen.vlogwebserviceserver.config.auth;

import com.maen.vlogwebserviceserver.config.auth.component.OAuth2SuccessHandler;
import com.maen.vlogwebserviceserver.config.auth.filter.JwtAuthFilter;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                        .antMatchers("/api/v1/token/**").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .logout().logoutSuccessUrl("/")
                .and()
                    .oauth2Login().loginPage("/api/v1/token/expired")
                        .successHandler(successHandler)
                        .userInfoEndpoint().userService(oAuth2UserService);

        http.addFilterBefore(new JwtAuthFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
    }
}
