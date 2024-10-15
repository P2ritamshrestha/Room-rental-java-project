package com.room_rental.com.stha.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String extractUsername(String token);
    boolean isValidToken(String token, UserDetails userDetails);
    String generateToken(String username);
    Object generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
