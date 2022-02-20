package com.maen.vlogwebserviceserver.config.auth;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class InMemoryProviderRepository {
    private final Map<String, OAuthProvider> providers;

    public OAuthProvider findByProviderName(String name) {
        return providers.get(name);
    }
}
