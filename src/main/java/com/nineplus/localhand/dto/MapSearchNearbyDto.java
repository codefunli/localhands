package com.nineplus.localhand.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MapSearchNearbyDto {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("currentLng")
    private Double currentLng;
    @JsonProperty("currentLat")
    private Double currentLat;
    @JsonProperty("searchRadius")
    private Integer searchRadius;
    @JsonProperty("limitation")
    private Integer limitation;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("priceMax")
    private Double priceMax;
    @JsonProperty("priceMin")
    private Double priceMin;
    @JsonProperty("categories")
    private List<Long> categories;
}
