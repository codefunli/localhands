package com.nineplus.localhand.dto.jpa_interface;

import java.math.BigDecimal;

public interface UserNearbyDto {
    Integer getUserId();
    String getLastGeoLng();
    String getLastGeoLat();
    String getAvatar();
    String getDistance();
}
