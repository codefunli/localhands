package com.nineplus.localhand.model;

import com.nineplus.localhand.enum_class.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "USER")
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 7629287212050058050L;
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Size(max = 32)
    @Column(name = "UUID", length = 32)
    private String uuid;

    @Size(max = 150)
    @Column(name = "USERNAME", length = 150)
    private String username;

    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Column(name = "BANNED", nullable = false)
    private Boolean banned = false;

    @Size(max = 255)
    @Column(name = "BAN_REASON")
    private String banReason;

    @Column(name = "BAN_AT")
    private Instant banAt;

    @Size(max = 40)
    @NotNull
    @Column(name = "LAST_IP", nullable = false, length = 40)
    private String lastIp;

    @Column(name = "LAST_LOGIN")
    private Instant lastLogin;

    @Size(max = 45)
    @Column(name = "LAST_GEO_LAT", length = 45)
    private String lastGeoLat;

    @Size(max = 45)
    @Column(name = "LAST_GEO_LNG", length = 45)
    private String lastGeoLng;

    @Column(name = "LAST_GEO_AT")
    private Instant lastGeoAt;

    @Size(max = 100)
    @Column(name = "FIRSTNAME", length = 100)
    private String firstName;

    @Size(max = 100)
    @Column(name = "LASTNAME", length = 100)
    private String lastName;

    @Size(max = 45)
    @Column(name = "NICKNAME", length = 45)
    private String nickname;

    @Size(max = 150)
    @Column(name = "EMAIL", length = 150)
    private String email;

    @Size(max = 45)
    @Column(name = "PHONE", length = 45)
    private String phone;

    @Column(name = "GENDER")
    private Character gender;

    @Column(name = "DOB")
    private LocalDate dob;

    @Size(max = 255)
    @Column(name = "AVATAR")
    private String avatar;

    @NotNull
    @Column(name = "SYS_CREATED_AT", nullable = false)
    private Instant sysCreatedAt;

    @Size(max = 32)
    @Column(name = "SYS_CREATED_BY", length = 32)
    private String sysCreatedBy;

    @NotNull
    @Column(name = "SYS_UPDATED_AT", nullable = false)
    private Instant sysUpdatedAt;

    @Size(max = 32)
    @Column(name = "SYS_UPDATED_BY", length = 32)
    private String sysUpdatedBy;

    @NotNull
    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @NotNull
    @Column(name = "IS_READY", nullable = false)
    private Boolean isReady = false;

}