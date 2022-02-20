package com.maen.vlogwebserviceserver.config.auth;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Getter
@ConfigurationProperties(prefix = "oauth2")
public class OAuthProperties {
    private final Map<String, Registration> registration = new HashMap<>();
    private final Map<String, Provider> provider = new HashMap<>();

    @Getter
    @Setter
    public static class Registration {
        private String clientId;
        private String clientSecret;
        private String redirectUri;
    }

    @Getter
    @Setter
    public static class Provider {
        private String authorizationUri;
        private String tokenUri;
        private String userInfoUri;
//        private String userNameAttribute;
    }

}
