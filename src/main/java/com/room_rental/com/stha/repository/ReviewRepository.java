package com.room_rental.com.stha.repository;

import com.room_rental.com.stha.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    @Query(value = "{ 'favorite': true }")
    List<Review> getBestReview();
}
