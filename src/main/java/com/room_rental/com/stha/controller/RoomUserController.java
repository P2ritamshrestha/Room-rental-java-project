package com.room_rental.com.stha.controller;

import com.room_rental.com.stha.DTO.ChangePasswordDTO;
import com.room_rental.com.stha.DTO.ProfileDTO;
import com.room_rental.com.stha.models.User;
import com.room_rental.com.stha.service.JwtService;
import com.room_rental.com.stha.service.RoomUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class RoomUserController {
    private final RoomUserService roomUserService;
    private final JwtService jwtService;


    @GetMapping("/extract-details")
    public ResponseEntity<?> extractDetailsFromToken(@RequestHeader("Authorization") String token) {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;

        String userEmail = jwtService.extractUserEmail(token);
        User user= roomUserService.getExtractDetails(userEmail);

        Map<String, Object> response = new HashMap<>();
        response.put("User Details", user);
        return ResponseEntity.ok(response);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> updateProfile(@PathVariable String id, @ModelAttribute @Valid ProfileDTO profileDTO) throws IOException {
         User user= roomUserService.updateProfile(id,profileDTO);
        return  ResponseEntity.ok(user);
    }


    @PostMapping("/{id}")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO, @PathVariable String id){
        roomUserService.changePassword(changePasswordDTO,id);
        return ResponseEntity.ok("Password changed successfully");
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        Resource imageResource = roomUserService.getImageAsResource(imageName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageName + "\"")
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .body(imageResource);
    }
}
