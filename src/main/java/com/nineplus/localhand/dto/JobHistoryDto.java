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
public class JobHistoryDto {
    @JsonProperty("jobId")
    private Long jobId;
    @JsonProperty("localId")
    private Integer localId;
    @JsonProperty("helperId")
    private Integer helperId;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("cancelBy")
    private Integer cancelBy;
}
