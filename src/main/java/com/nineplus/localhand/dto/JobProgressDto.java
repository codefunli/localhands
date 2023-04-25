package com.nineplus.localhand.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobProgressDto {
    @JsonProperty("jobId")
    private Long jobId;
    @JsonProperty("actionBy")
    private Long actionBy;
    @JsonProperty("cancelReason")
    private String reason;
}
