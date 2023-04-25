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
@Entity(name = "JOBHISTORY")
@Table(name = "job_history")
public class JobHistory implements Serializable {
    @Serial
    private static final long serialVersionUID = -6630300618217004490L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Column(name = "JOB_ID", columnDefinition = "INT UNSIGNED not null")
    private Long jobId;

    @Column(name = "LOCAL_ID", columnDefinition = "INT UNSIGNED not null")
    private Long localId;

    @Column(name = "HELPER_ID", columnDefinition = "INT UNSIGNED")
    private Long helperId;

    @Column(name = "JOB_STATUS", columnDefinition = "INT UNSIGNED not null")
    @Enumerated(EnumType.ORDINAL)
    private JobStatus jobStatus;

    @Size(max = 1000)
    @Column(name = "CANCEL_REASON", length = 1000)
    private String cancelReason;

    @Column(name = "CANCEL_BY", columnDefinition = "INT UNSIGNED")
    private Long cancelBy;

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