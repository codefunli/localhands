package com.nineplus.localhand.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JobDto extends BaseDto {

    @JsonProperty("jobId")
    private Long id;

    @JsonProperty("jobDate")
    private String jobDate;

    @JsonProperty("jobCategoryId")
    private Long jobCategoryId;

    @JsonProperty("jobTitle")
    private String jobTitle;

    @JsonProperty("jobAddress")
    private String jobAddress;

    @JsonProperty("jobDescription")
    private String jobDescription;

    @JsonProperty("jobEstimatedTime")
    private Integer jobEstimatedTime;

    @JsonProperty("jobPrice")
    private Double jobPrice;

    @JsonProperty("jobStatus")
    private Integer jobStatus;

    @JsonProperty("jobMediaPicture")
    private List<byte[]> jobMediaPicture;

    @JsonProperty("localUserId")
    private Long localUserId;

    @JsonProperty("localGeoLat")
    private String localGeoLat;

    @JsonProperty("localGeoLng")
    private String localGeoLng;

    @JsonProperty("helperUserId")
    private Long helperUserId;

    @JsonProperty("helperGeoLat")
    private String helperGeoLat;

    @JsonProperty("helperGeoLng")
    private String helperGeoLng;

    @JsonProperty("actionBy")
    private Long actionBy;

    @JsonProperty("reason")
    private String reason;
}
