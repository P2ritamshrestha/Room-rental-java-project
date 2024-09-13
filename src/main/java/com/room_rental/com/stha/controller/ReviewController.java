package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.DTO.ReviewDTO;
import com.room_rental.com.stha.service.JwtService;
import com.room_rental.com.stha.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> saveReview(@RequestHeader("Authorization") String token, ReviewDTO reviewDTO) throws IOException {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;

        String username = jwtService.extractUserName(token);
        reviewService.addReview(username,reviewDTO);

        return ResponseEntity.ok("Review add successfully");
    }
}
