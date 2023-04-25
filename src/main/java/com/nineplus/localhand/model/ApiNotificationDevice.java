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
@Entity(name = "APINOTIFICATIONDEVICE")
@Table(name = "api_notification_device")
public class ApiNotificationDevice implements Serializable {
    private static final long serialVersionUID = -1691479781523931312L;
    @Id
    @Column(name = "ID", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Size(max = 36)
    @NotNull
    @Column(name = "USER_ID", nullable = false, length = 36)
    private String userId;

    @Lob
    @Column(name = "PUSH_TOKEN")
    private String pushToken;

    @Size(max = 45)
    @Column(name = "APP_CODE", length = 45)
    private String appCode;

    @Size(max = 20)
    @Column(name = "APP_VERSION", length = 20)
    private String appVersion;

    @Size(max = 2)
    @NotNull
    @Column(name = "LANGUAGE", nullable = false, length = 2)
    private String language;

    @Size(max = 255)
    @Column(name = "HWID")
    private String hwid;

    @NotNull
    @Column(name = "TIMEZONE", nullable = false)
    private Integer timezone;

    @NotNull
    @Column(name = "DEVICE_TYPE", nullable = false)
    private Byte deviceType;

    @Size(max = 45)
    @Column(name = "DEVICE_PLATFORM", length = 45)
    private String devicePlatform;

    @Size(max = 100)
    @Column(name = "DEVICE_MANUFACTURER", length = 100)
    private String deviceManufacturer;

    @Size(max = 100)
    @Column(name = "DEVICE_MODEL", length = 100)
    private String deviceModel;

    @Size(max = 25)
    @Column(name = "DEVICE_VERSION", length = 25)
    private String deviceVersion;

    @Size(max = 45)
    @Column(name = "BROWSER_TYPE", length = 45)
    private String browserType;

    @Size(max = 45)
    @Column(name = "BROWSER_MODEL", length = 45)
    private String browserModel;

    @Size(max = 25)
    @Column(name = "BROWSER_VERSION", length = 25)
    private String browserVersion;

    @Size(max = 255)
    @Column(name = "BROWSER_USERAGENT")
    private String browserUseragent;

    @NotNull
    @Column(name = "STATUS", nullable = false)
    private Boolean status = false;

    @NotNull
    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

    @Column(name = "UPDATED_AT")
    private Instant updatedAt;

}