package com.nineplus.localhand.model;

import com.nineplus.localhand.enum_class.RatingType;
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
@Entity(name = "JOBRATING")
@Table(name = "job_rating")
public class JobRating implements Serializable {
    private static final long serialVersionUID = 75578273982865141L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RATING_ID", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Column(name = "TASK_ID", columnDefinition = "INT UNSIGNED")
    private Long taskId;

    @Column(name = "USER_ID", columnDefinition = "INT UNSIGNED")
    private Long userId;

    @Column(name = "RATING_TYPE", columnDefinition = "TINYINT UNSIGNED")
    @Enumerated(EnumType.ORDINAL)
    private RatingType ratingType;

    @Column(name = "RATING_RECOMMENDATION")
    private Boolean ratingRecommendation;

    @Column(name = "RATING_STAR1", columnDefinition = "TINYINT UNSIGNED")
    private Short ratingStar1;

    @Column(name = "RATING_STAR2", columnDefinition = "TINYINT UNSIGNED")
    private Short ratingStar2;

    @Column(name = "RATING_STAR3", columnDefinition = "TINYINT UNSIGNED")
    private Short ratingStar3;

    @Size(max = 255)
    @Column(name = "RATING_COMMENT_PRIVATE")
    private String ratingCommentPrivate;

    @Size(max = 255)
    @Column(name = "RATING_COMMENT_PUBLIC")
    private String ratingCommentPublic;

    @NotNull
    @Column(name = "RATING_DATE_RATED", nullable = false)
    private Instant ratingDateRated;

    @NotNull
    @Column(name = "RATING_DATE_APPROVED", nullable = false)
    private Instant ratingDateApproved;

    @NotNull
    @Column(name = "RATING_STATUS", nullable = false)
    private Boolean ratingStatus = false;

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