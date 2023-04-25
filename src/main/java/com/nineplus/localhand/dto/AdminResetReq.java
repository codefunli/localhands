package com.nineplus.localhand.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminResetReq extends BaseDto{

    private static final long serialVersionUID = 1989836694233557629L;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("newPassword")
    private String newPassword;
}
