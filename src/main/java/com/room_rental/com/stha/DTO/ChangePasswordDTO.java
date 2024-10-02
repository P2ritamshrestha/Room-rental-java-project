package com.room_rental.com.stha.DTO;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String currentPassword;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$", message = "Password must be at least 8 characters long, contain at least one uppercase letter, one digit, and one special character.")
    private String newPassword;
    private String confirmPassword;
}
