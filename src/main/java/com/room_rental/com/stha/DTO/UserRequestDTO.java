package com.room_rental.com.stha.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Getter
@Setter
public class UserRequestDTO {
    @NotBlank
    private String fullName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String password;
    @NotBlank
    private String conformPassword;
    @NotBlank
    private String address;
}
