package com.room_rental.com.stha.DTO;

import com.room_rental.com.stha.validation.UniqueEmail;
import com.room_rental.com.stha.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "Full name not blank")
    private String fullName;

    @NotBlank
    @Email(message = "Invalid email")
    @UniqueEmail
    private String email;

    @NotBlank(message = "Username not blank")
    @UniqueUsername
    private String uniqueName;

    @NotBlank
    @Pattern(regexp = "^98\\d{8}$", message = "Phone number must start with 98 and be exactly 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Password not blank")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one digit, and one special character.")
    private String password;

    @NotBlank(message = "Confirm password not blank")
    private String confirmPassword;

    @NotBlank
    private String address;
}
