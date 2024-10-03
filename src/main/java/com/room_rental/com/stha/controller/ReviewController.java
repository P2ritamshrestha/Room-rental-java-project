package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.DTO.ReviewDTO;
import com.room_rental.com.stha.service.JwtService;
import com.room_rental.com.stha.service.ReviewService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> saveReview(@RequestHeader("Authorization") String token, @RequestBody @Valid ReviewDTO reviewDTO) throws IOException {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;

        String username = jwtService.extractUserName(token);
        reviewService.addReview(username,reviewDTO);

        return ResponseEntity.ok("Review add successfully");
    }

    @RequestMapping("/{reviewId}")
    private ResponseEntity<?> getByReviewId(@RequestHeader("Authorization") String token,@PathVariable String reviewId){
        token = token.startsWith("Bearer ")? token.substring(7):token;
        String username = jwtService.extractUserName(token);
        return ResponseEntity.ok(reviewService.getReviewById(username,reviewId));
    }

    @DeleteMapping("/{reviewId}")
    private ResponseEntity<?> deleteReview(@PathVariable String reviewId){
        reviewService.deleteReviewById(reviewId);
        return ResponseEntity.ok("Review delete successfully");
    }
}

