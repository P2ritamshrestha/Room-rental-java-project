package com.room_rental.com.stha.repository;

import com.room_rental.com.stha.models.MultiImages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pritam shrestha
 * @version v1.0
 * @date 2024-11-10
 **/

@Repository
public interface MultiImageRepo extends MongoRepository<MultiImages, String> {

}
