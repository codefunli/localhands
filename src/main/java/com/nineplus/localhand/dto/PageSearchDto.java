package com.nineplus.localhand.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Sort;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = false)
public class PageSearchDto extends BaseDto {
    @Serial
    private static final long serialVersionUID = 6845442412597287817L;

    @JsonProperty("page")
    private String page;

    @JsonProperty("size")
    private String size;

    @JsonProperty("sortDirection")
    private Sort.Direction sortDirection;

    @JsonProperty("sortBy")
    private String sortBy;

    @JsonProperty("code")
    private String code;

    @JsonProperty("fromDate")
    private String fromDate;

    @JsonProperty("toDate")
    private String toDate;

    @JsonProperty("ownerName")
    private String ownerName;

    @JsonProperty("receiverName")
    private String receiverName;

    @JsonProperty("status")
    private int status;
    
    @JsonProperty("templateName")
    private String templateName;
    
    @JsonProperty("createdUser")
    private String createdUser;

    @JsonProperty("templateId")
    private String templateId;
}
