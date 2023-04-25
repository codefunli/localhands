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
@Entity(name = "JOBCATEGORY")
@Table(name = "job_category")
public class JobCategory implements Serializable {
    private static final long serialVersionUID = -300087906093404924L;
    @Id
    @Column(name = "CATEGORY_ID", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Size(max = 150)
    @Column(name = "CATEGORY_SHORTCUT", length = 150)
    private String categoryShortcut;

    @Size(max = 150)
    @Column(name = "CATEGORY_NAME", length = 150)
    private String categoryName;

    @Size(max = 500)
    @Column(name = "CATEGORY_DESCRIPTION", length = 500)
    private String categoryDescription;

    @NotNull
    @Column(name = "CATEGORY_STATUS", nullable = false)
    private Boolean categoryStatus = false;

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

    @Column(name = "ICON")
    private String icon;
}