package com.room_rental.com.stha.service;

import com.room_rental.com.stha.DTO.ReviewDTO;
import com.room_rental.com.stha.models.Review;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    Review addReview(String username, ReviewDTO reviewDTO) throws IOException;
//    List<ReviewDTO> getAllReviews();
    ReviewDTO getReviewById(String username, String id);
    void deleteReviewById(String id);


    List<Review> getAllReview(String username);
}
