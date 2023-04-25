package com.nineplus.localhand.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "USERPROFILE")
@Table(name = "user_profiles")
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 466071232683475545L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "USER_ID", columnDefinition = "INT UNSIGNED not null")
    private Long userId;

    @Size(max = 255)
    @Column(name = "HOME_STREET")
    private String homeStreet;

    @Size(max = 150)
    @Column(name = "HOME_CITY", length = 150)
    private String homeCity;

    @Size(max = 10)
    @Column(name = "HOME_ZIP", length = 10)
    private String homeZip;

    @Size(max = 45)
    @Column(name = "HOME_STATE", length = 45)
    private String homeState;

    @Size(max = 45)
    @Column(name = "HOME_COUNTRY", length = 45)
    private String homeCountry;

    @Size(max = 45)
    @Column(name = "HOME_GEO_LAT", length = 45)
    private String homeGeoLat;

    @Size(max = 45)
    @Column(name = "HOME_GEO_LNG", length = 45)
    private String homeGeoLng;

    @Size(max = 255)
    @Column(name = "HANDLER_MEDIA_DRIVERLICENSE")
    private String handlerMediaDriverlicense;

    @Size(max = 45)
    @Column(name = "HANDLER_TYPE_VEHICLE", length = 45)
    private String handlerTypeVehicle;

    @Column(name = "HANDLER_ALLOW_BACKGROUNDCHECK", columnDefinition = "TINYINT UNSIGNED")
    private Boolean handlerAllowBackgroundcheck;

    @Size(max = 45)
    @Column(name = "PAYMENT_STRIPE_CUSTOMER_ID", length = 45)
    private String paymentStripeCustomerId;

    @Size(max = 45)
    @Column(name = "PAYMENT_CARD_NAME", length = 45)
    private String paymentCardName;

    @Size(max = 45)
    @Column(name = "PAYMENT_CARD_TYPE", length = 45)
    private String paymentCardType;

    @Size(max = 45)
    @Column(name = "PAYMENT_CARD_NUMBER", length = 45)
    private String paymentCardNumber;

    @Size(max = 45)
    @Column(name = "PAYMENT_CARD_SECURITY", length = 45)
    private String paymentCardSecurity;

    @Size(max = 45)
    @Column(name = "PAYMENT_CARD_EXPIRATION", length = 45)
    private String paymentCardExpiration;

    @Size(max = 150)
    @Column(name = "PAYMENT_BANK", length = 150)
    private String paymentBank;

    @Size(max = 150)
    @Column(name = "PAYMENT_PAYPAL", length = 150)
    private String paymentPaypal;

    @Size(max = 150)
    @Column(name = "PAYMENT_VENMO", length = 150)
    private String paymentVenmo;

    @NotNull
    @Column(name = "SYS_CREATED_AT", nullable = false)
    private Instant sysCreatedAt;

    @Column(name = "SYS_CREATED_BY", columnDefinition = "INT UNSIGNED")
    private Long sysCreatedBy;

    @NotNull
    @Column(name = "SYS_UPDATED_AT", nullable = false)
    private Instant sysUpdatedAt;

    @Column(name = "SYS_UPDATED_BY", columnDefinition = "INT UNSIGNED")
    private Long sysUpdatedBy;

    @Column(name = "BIO")
    private String bio;

}