package com.room_rental.com.stha.service.impl;

import com.room_rental.com.stha.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    public CustomOAuth2SuccessHandler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String jwtToken = null;

        // Check if the principal is an OIDC user
        if (authentication.getPrincipal() instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            jwtToken = jwtService.generateToken(oidcUser.getEmail());

            // Check if the principal is a regular OAuth2 user
        } else if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
            jwtToken = jwtService.generateToken(oauth2User.getAttribute("email"));
        }

        // Redirect with the JWT token as a query parameter
        String redirectUrl = "http://localhost:5173/?token=" + jwtToken;
        response.sendRedirect(redirectUrl);
    }
}
