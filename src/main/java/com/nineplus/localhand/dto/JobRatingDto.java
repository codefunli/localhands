package com.nineplus.localhand.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JobRatingDto extends BaseDto{

    private static final long serialVersionUID = -5430293262991096908L;

    @JsonProperty("ratingId")
    private Long id;

    @JsonProperty("jobId")
    private Long taskId;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("recommendation")
    private Boolean ratingRecommendation;

    @JsonProperty("ratingStar1")
    private Short ratingStar1;

    @JsonProperty("ratingStar2")
    private Short ratingStar2;

    @JsonProperty("ratingStar3")
    private Short ratingStar3;

    @JsonProperty("privateComment")
    private String ratingCommentPrivate;

    @JsonProperty("publicComment")
    private String ratingCommentPublic;

    @JsonProperty("tip")
    private Double tip;

}
