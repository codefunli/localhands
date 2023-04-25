package com.nineplus.localhand.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PageResDto<T> extends BaseDto {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7969015153524943561L;

    @JsonProperty("content")
    private List<T> content;

    @JsonProperty("metaData")
    private ContentPageDto metaData;

}