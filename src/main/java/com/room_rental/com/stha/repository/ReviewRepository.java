package com.room_rental.com.stha.repository;

import com.room_rental.com.stha.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {
}
