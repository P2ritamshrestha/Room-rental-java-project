package com.room_rental.com.stha.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "review")
public class Review {
    @Id
    private String id;
    private String title;
    private String description;
    private String imagePath;
    private String imageName;
    private String activity;
    private Integer rating;
    // how to map user Many to one
    @DBRef
    private User user;

}
