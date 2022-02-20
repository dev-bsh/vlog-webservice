package com.maen.vlogwebserviceserver.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OAuthProperties.class)
public class OAuthConfig {

    private final OAuthProperties oAuthProperties;

    //application-oauth.properties 값들을 InMemoryProviderRepository에 객체들로 저장
    @Bean
    public InMemoryProviderRepository inMemoryProviderRepository() {
        Map<String, OAuthProvider> providers = OAuthAdapter.getOAuthProviders(oAuthProperties);
        return new InMemoryProviderRepository(providers);
    }

}
