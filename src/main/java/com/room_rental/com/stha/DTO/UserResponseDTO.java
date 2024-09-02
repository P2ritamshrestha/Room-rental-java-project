package com.room_rental.com.stha.DTO;

import com.room_rental.com.stha.models.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String id;
    private String fullName;
    private String email;
    private String username;
    private String phoneNumber;
    private String address;
    private Role role;
}
