package com.nineplus.localhand.service;

import com.nineplus.localhand.dto.MapSearchNearbyDto;
import com.nineplus.localhand.dto.jpa_interface.JobNearbyDto;
import com.nineplus.localhand.dto.jpa_interface.UserNearbyDto;

import java.util.List;

public interface MapServices {
    List<UserNearbyDto> findUsersNearby(MapSearchNearbyDto nearbyDto);

    List<JobNearbyDto> findJobsNearBy(MapSearchNearbyDto nearbyDto);
}
