package com.nineplus.localhand.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nineplus.localhand.enum_class.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto extends UserUpdateDto{

    @JsonProperty("username")
    private String username;

    @JsonProperty("banned")
    private Boolean banned;

    @JsonProperty("banReason")
    private String banReason;

    @JsonProperty("banAt")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Instant banAt;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("gender")
    private Character gender;

    @JsonProperty("dob")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("isReady")
    private Boolean isReady;

    @JsonProperty("avatar")
    private byte[] avatar;

    @JsonProperty("driverLicenseFront")
    private byte[] driverLicenseFront;

    @JsonProperty("driverLicenseBack")
    private byte[] driverLicenseBack;

}
