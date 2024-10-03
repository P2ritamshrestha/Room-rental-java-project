package com.room_rental.com.stha.DTO;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private MultipartFile image;
    private  String username;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$", message = "Password must be at least 8 characters long, contain at least one uppercase letter, one digit, and one special character.")
    private String phone;
    private String address;
}
