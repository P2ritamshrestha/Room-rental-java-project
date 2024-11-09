package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author pritam shrestha
 * @version v1.0
 * @date 2024-11-09
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final RoomService  roomService;

    @PostMapping("/{roomId}")
    public ResponseEntity<?> setTrueOrFalseWishlist(@PathVariable String roomId) throws IOException {
        roomService.setTrueOrFalseWishlist(roomId);
        return ResponseEntity.ok("Wishlist set successfully");
    }


}
