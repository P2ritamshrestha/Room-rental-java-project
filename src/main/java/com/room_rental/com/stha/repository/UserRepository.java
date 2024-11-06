package com.room_rental.com.stha.repository;

import com.room_rental.com.stha.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ '$or': [ { 'email': ?0 }, { 'username': ?0 } ] }")
    Optional<User> findByUsernameOrEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
