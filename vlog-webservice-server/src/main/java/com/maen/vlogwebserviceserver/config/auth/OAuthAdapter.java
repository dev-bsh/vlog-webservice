package com.maen.vlogwebserviceserver.config.auth;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class OAuthAdapter {

    public static Map<String, OAuthProvider> getOAuthProviders(OAuthProperties properties) {
        Map<String, OAuthProvider> providers = new HashMap<>();

        //key : provider 이름, value = registration 객체 (clientId,clientSecret, redirectUri)
        properties.getRegistration().forEach((key, value) -> providers.put(key,
                new OAuthProvider(value, properties.getProvider().get(key))));
        return providers;
    }
}
