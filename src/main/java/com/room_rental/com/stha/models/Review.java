package com.room_rental.com.stha.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "review")
public class Review {
    @Id
    private String reviewId;
    private String message;
    private String imagePath;
    private String imageName;
    private Integer rating;
    private boolean favorite;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    @DBRef
    private User user;

}
