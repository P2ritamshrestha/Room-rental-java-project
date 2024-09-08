package com.room_rental.com.stha.DTO;

import com.room_rental.com.stha.models.Role;
import com.room_rental.com.stha.validation.UniqueEmail;
import com.room_rental.com.stha.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@RequiredArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "Full name not blank")
    private String fullName;

    @Email(message = "Invalid email")
    @UniqueEmail
    private String email;

    @NotBlank(message = "Username not blank")
    @UniqueUsername
    private String username;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Password not blank")
    private String password;

    @NotBlank(message = "Confirm password not blank")
    private String confirmPassword;

    private String address;
}
