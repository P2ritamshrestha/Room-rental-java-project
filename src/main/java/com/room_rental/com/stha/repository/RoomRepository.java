package com.room_rental.com.stha.repository;

import com.room_rental.com.stha.models.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {
}
