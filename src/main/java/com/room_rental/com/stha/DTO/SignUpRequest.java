package com.room_rental.com.stha.DTO;

import com.room_rental.com.stha.models.Role;
import lombok.Data;

@Data
public class SignUpRequest {
    private String fullName;
    private String password;
    private String confirmPassword;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private Role role;
}
