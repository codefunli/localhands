package com.nineplus.localhand.dto.jpa_interface;

public interface JobNearbyDto {
    Integer getJobId();
    Double getGeoLat();
    Double getGeoLng();
    Double getPrice();
    Double getDistance();
}
