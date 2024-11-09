package com.room_rental.com.stha.service;

import com.room_rental.com.stha.DTO.RoomRequestDTO;
import com.room_rental.com.stha.models.Room;

import java.io.IOException;
import java.util.List;

public interface RoomService {
    Room createRoom(RoomRequestDTO room) throws IOException;
    void deleteRoom(String roomId);
    Room updateRoom(RoomRequestDTO roomRequestDTO);
    Room getRoom(String roomId);
    List<Room> getAllRooms();

    void setTrueOrFalseWishlist(String roomId);
}
