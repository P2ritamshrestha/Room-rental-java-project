package com.room_rental.com.stha.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AuthenticationServiceImpl authenticationServiceImpl;

    public CustomOAuth2UserService(@Lazy AuthenticationServiceImpl authenticationServiceImpl) {
        this.authenticationServiceImpl = authenticationServiceImpl;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // Extract user information (like email, name) from OAuth2 provider (e.g., Google)
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        // Convert userRequest to OAuth2AuthenticationToken to process in AuthenticationServiceImpl
        OAuth2AuthenticationToken authenticationToken = new OAuth2AuthenticationToken(
                oauth2User,
                oauth2User.getAuthorities(),
                userRequest.getClientRegistration().getRegistrationId()
        );

        // Save or update user in the database
        try {
            authenticationServiceImpl.processOAuthPostLogin(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return oauth2User;
    }
}
