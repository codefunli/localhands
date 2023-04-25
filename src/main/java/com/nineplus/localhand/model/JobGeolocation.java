package com.nineplus.localhand.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Entity(name = "JOBGEOLOCATION")
@Table(name = "job_geolocation")
public class JobGeolocation implements Serializable {
    private static final long serialVersionUID = 3769810643653190716L;
    @Id
    @Column(name = "GEO_ID", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Column(name = "JOB_ID", columnDefinition = "INT UNSIGNED")
    private Long jobId;

    @Column(name = "USER_ID", columnDefinition = "INT UNSIGNED")
    private Long userId;

    @Size(max = 20)
    @Column(name = "GEO_LAT", length = 20)
    private String geoLat;

    @Size(max = 20)
    @Column(name = "GEO_LNG", length = 20)
    private String geoLng;

    @NotNull
    @Column(name = "GEO_CREATED_AT", nullable = false)
    private Instant geoCreatedAt;

}