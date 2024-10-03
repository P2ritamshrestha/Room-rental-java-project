package com.room_rental.com.stha.DTO;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {

    @NotNull(message = "Message cannot be null")
    @Size(max = 160, message = "Message cannot exceed 160 characters")
    private String message;
    private MultipartFile image;

    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private Integer rating;
    private Date createdDate;
    private String fullName;
}
