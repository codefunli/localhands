package com.nineplus.localhand.model;

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
@Entity(name = "APP_PARAM")
@Table(name = "app_param")
public class AppParam implements Serializable {
    @Serial
    private static final long serialVersionUID = -3222201127799668398L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PARAM", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "PARAM_NAME", nullable = false, length = 100)
    private String paramName;

    @Size(max = 1000)
    @NotNull
    @Column(name = "PARAM_DESCRIPTION", nullable = false, length = 1000)
    private String paramDescription;

    @Size(max = 100)
    @Column(name = "PARAM_VALUE", length = 100)
    private String paramValue;

    @NotNull
    @Column(name = "SYS_CREATED_AT", nullable = false)
    private Instant sysCreatedAt;

    @Size(max = 150)
    @Column(name = "SYS_CREATED_BY", length = 150)
    private String sysCreatedBy;

    @NotNull
    @Column(name = "SYS_UPDATED_AT", nullable = false)
    private Instant sysUpdatedAt;

    @Size(max = 150)
    @Column(name = "SYS_UPDATED_BY", length = 150)
    private String sysUpdatedBy;

}