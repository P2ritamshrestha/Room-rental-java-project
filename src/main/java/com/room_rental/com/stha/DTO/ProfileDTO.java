package com.room_rental.com.stha.DTO;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Pattern(regexp = "^98\\d{8}$", message = "Phone number must start with 98 and be exactly 10 digits")
    private String phoneNumber;
    @NotBlank
    private String address;
}
