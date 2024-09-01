package com.room_rental.com.stha.models;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "user_details")
public class User {
    @Id
    private String id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String password;

    private String conformPassword;

    private String address;
}
