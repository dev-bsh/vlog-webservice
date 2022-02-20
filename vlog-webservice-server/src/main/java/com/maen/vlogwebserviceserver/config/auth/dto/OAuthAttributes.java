package com.maen.vlogwebserviceserver.config.auth.dto;


import com.maen.vlogwebserviceserver.domain.user.Role;
import com.maen.vlogwebserviceserver.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String providerName, Map<String, Object> attributes) {
        if(providerName.equals("naver")) {
            return ofNaver(attributes);
        }
        else if(providerName.equals("kakao")) {
            return ofKaKao(attributes);
        }
        else {
            return ofGoogle(attributes);
        }
    }

    private static OAuthAttributes ofGoogle(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .build();
    }
    private static OAuthAttributes ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .build();
    }
    private static OAuthAttributes ofKaKao(Map<String, Object> attributes) {
        //email은 acoount, name picture는 profile에
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) kakaoProfile.get("profile_image_url"))
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }


}
