package com.nineplus.localhand.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("role")
    private Integer role;

    @JsonProperty("address")
    private String homeStreet;

    @JsonProperty("city")
    private String homeCity;

    @JsonProperty("state")
    private String homeState;

    @JsonProperty("zip")
    private String homeZip;

    @JsonProperty("country")
    private String homeCountry;

    @JsonProperty("otp")
    private String otp;

    @JsonProperty("jobCategory")
    private List<Integer> jobCategory;

    @JsonProperty("helperPaypal")
    private String paymentPaypal;

    @JsonProperty("helperBankAccount")
    private String paymentBank;

    @JsonProperty("helperVenmo")
    private String paymentVenmo;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("vehicle")
    private String handlerTypeVehicle;

    @JsonProperty("backgroundCheck")
    private Boolean handlerAllowBackgroundcheck;

    @JsonProperty("lat")
    private String homeGeoLat;

    @JsonProperty("lng")
    private String homeGeoLng;

    @JsonProperty("localCardName")
    private String paymentCardName;

    @JsonProperty("localCardType")
    private String paymentCardType;

    @JsonProperty("localCardNumber")
    private String paymentCardNumber;

    @JsonProperty("localCardSecurity")
    private String paymentCardSecurity;

    @JsonProperty("localCardExpiration")
    private String paymentCardExpiration;

}
