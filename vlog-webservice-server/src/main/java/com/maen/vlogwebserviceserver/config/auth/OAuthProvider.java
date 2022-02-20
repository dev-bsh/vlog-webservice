package com.maen.vlogwebserviceserver.config.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthProvider {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String authorizationUri;
    private final String tokenUri;
    private final String userInfoUri;

    public OAuthProvider(OAuthProperties.Registration registration, OAuthProperties.Provider provider) {
        this(registration.getClientId(),registration.getClientSecret(), registration.getRedirectUri(), provider.getAuthorizationUri(), provider.getTokenUri(), provider.getUserInfoUri());
    }

    @Builder
    public OAuthProvider(String clientId, String clientSecret, String redirectUri, String authorizationUri, String tokenUri, String userInfoUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.authorizationUri = authorizationUri;
        this.tokenUri = tokenUri;
        this.userInfoUri = userInfoUri;
    }

}
