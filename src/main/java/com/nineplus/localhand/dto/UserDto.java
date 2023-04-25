package com.nineplus.localhand.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonProperty("userId")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("roleName")
    private String roleName;

    @NotBlank(message = "Firstname cannot be blank")
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank(message = "Lastname cannot be blank")
    @JsonProperty("lastName")
    private String lastName;

    @Email(message = "Email invalid")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Phone numbers cannot be blank")
    @JsonProperty("phone")
    private String phone;

    @JsonProperty("otp")
    private String otp;
}
