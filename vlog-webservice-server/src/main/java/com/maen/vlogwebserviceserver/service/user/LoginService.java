package com.maen.vlogwebserviceserver.service.user;


import com.maen.vlogwebserviceserver.config.auth.InMemoryProviderRepository;
import com.maen.vlogwebserviceserver.config.auth.JwtService;
import com.maen.vlogwebserviceserver.config.auth.OAuthProvider;
import com.maen.vlogwebserviceserver.config.auth.dto.Jwt;
import com.maen.vlogwebserviceserver.config.auth.dto.OAuthAttributes;
import com.maen.vlogwebserviceserver.domain.user.User;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import com.maen.vlogwebserviceserver.web.dto.LoginResponseDto;
import com.maen.vlogwebserviceserver.config.auth.dto.OAuthTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final InMemoryProviderRepository inMemoryProviderRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public StringBuilder getAuthorizationCode(String providerName) {
        OAuthProvider oAuthProvider = inMemoryProviderRepository.findByProviderName(providerName);
        StringBuilder loginUri = new StringBuilder();
        loginUri.append(oAuthProvider.getAuthorizationUri())
                .append("?client_id=").append(oAuthProvider.getClientId())
                .append("&redirect_uri=").append(oAuthProvider.getRedirectUri())
                .append("&response_type=code");
        if(providerName.equals("google")) {
            loginUri.append("&scope=https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email");
        }
        return loginUri;
    }

    @Transactional
    public LoginResponseDto login(String providerName, String code) {
        OAuthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);
        //provider에서 auth-code로 받아온 access token
        OAuthTokenResponse oAuthTokenResponse = getOAuthToken(provider, code);
        //토큰으로 user data 받아오기
        OAuthAttributes userProfile = getUserProfile(providerName, oAuthTokenResponse, provider);
        //user data 받아와서 db에 저장
        User user = saveOrUpdate(userProfile);
        //jwt생성
        Jwt jwt = jwtService.generateToken(String.valueOf(user.getId()));

        return LoginResponseDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .picture(user.getPicture())
                .accessToken(jwt.getAccessToken())
                .refreshToken(jwt.getRefreshToken())
                .tokenType("Bearer")
                .build();
    }


    private OAuthTokenResponse getOAuthToken(OAuthProvider provider, String code) {
        return WebClient.create()
                .post()
                .uri(provider.getTokenUri())
                .headers(header -> {
                    header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(oauthTokenRequest(provider, code))
                .retrieve()
                .bodyToMono(OAuthTokenResponse.class)
                .block();
    }

    private MultiValueMap<String, String> oauthTokenRequest(OAuthProvider provider, String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code",code);
        formData.add("grant_type","authorization_code");
        formData.add("redirect_uri",provider.getRedirectUri());
        return formData;
    }

    private OAuthAttributes getUserProfile(String providerName, OAuthTokenResponse oAuthTokenResponse, OAuthProvider provider) {
        Map<String, Object> userAttributes = getUserAttributes(provider, oAuthTokenResponse);
        return OAuthAttributes.of(providerName, userAttributes);
    }

    private Map<String, Object> getUserAttributes(OAuthProvider provider, OAuthTokenResponse oAuthTokenResponse) {
        return WebClient.create()
                .get()
                .uri(provider.getUserInfoUri())
                .headers(header -> header.setBearerAuth(oAuthTokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }




}
