package com.nineplus.localhand.model;

import com.nineplus.localhand.enum_class.JobStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "JOB")
@Table(name = "job")
public class Job implements Serializable {
    @Serial
    private static final long serialVersionUID = -6763785645389273038L;
    @Id
    @Column(name = "JOB_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "JOB_DATE", nullable = false)
    private Instant jobDate;

    @Column(name = "JOB_CATEGORY_ID", columnDefinition = "INT UNSIGNED not null")
    private Long jobCategoryId;

    @Size(max = 500)
    @Column(name = "JOB_TITLE", length = 500)
    private String jobTitle;

    @Size(max = 500)
    @Column(name = "JOB_ADDRESS", length = 500)
    private String jobAddress;

    @Size(max = 20)
    @Column(name = "JOB_GEO_LAT", length = 20)
    private String jobGeoLat;

    @Size(max = 20)
    @Column(name = "JOB_GEO_LNG", length = 20)
    private String jobGeoLng;

    @Size(max = 500)
    @Column(name = "JOB_DESCRIPTION", length = 500)
    private String jobDescription;

    @Column(name = "JOB_ESTIMATED_TIME")
    private Integer jobEstimatedTime;

    @Column(name = "JOB_PRICE")
    private Double jobPrice;

    @NotNull
    @Column(name = "JOB_STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private JobStatus jobStatus;

    @Size(max = 255)
    @Column(name = "JOB_MEDIA_PICTURE")
    private String jobMediaPicture;

    @Column(name = "LOCAL_USER_ID", columnDefinition = "INT UNSIGNED not null")
    private Long localUserId;

    @Size(max = 20)
    @Column(name = "LOCAL_GEO_LAT", length = 20)
    private String localGeoLat;

    @Size(max = 20)
    @Column(name = "LOCAL_GEO_LNG", length = 20)
    private String localGeoLng;

    @Column(name = "HELPER_USER_ID")
    private Long helperUserId;

    @Size(max = 20)
    @Column(name = "HELPER_GEO_LAT", length = 20)
    private String helperGeoLat;

    @Size(max = 20)
    @Column(name = "HELPER_GEO_LNG", length = 20)
    private String helperGeoLng;

    @Column(name = "HELPER_ACCEPTED_AT")
    private Instant helperAcceptedAt;

    @NotNull
    @Column(name = "SYS_CREATED_AT", nullable = false)
    private Instant sysCreatedAt;

    @Column(name = "SYS_CREATED_BY", columnDefinition = "INT UNSIGNED")
    private String sysCreatedBy;

    @NotNull
    @Column(name = "SYS_UPDATED_AT", nullable = false)
    private Instant sysUpdatedAt;

    @Column(name = "SYS_UPDATED_BY", columnDefinition = "INT UNSIGNED")
    private String sysUpdatedBy;

}