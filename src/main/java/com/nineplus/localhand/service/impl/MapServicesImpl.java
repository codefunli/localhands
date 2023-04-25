package com.nineplus.localhand.service.impl;

import com.nineplus.localhand.dto.MapSearchNearbyDto;
import com.nineplus.localhand.dto.jpa_interface.JobNearbyDto;
import com.nineplus.localhand.dto.jpa_interface.UserNearbyDto;
import com.nineplus.localhand.repository.JobRepository;
import com.nineplus.localhand.repository.UserRepository;
import com.nineplus.localhand.service.MapServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class MapServicesImpl implements MapServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;

    @Override
    @Transactional
    public List<UserNearbyDto> findUsersNearby(MapSearchNearbyDto nearbyDto) {
        return userRepository.getUsersNearBy(nearbyDto.getId(),
                nearbyDto.getCurrentLng(),
                nearbyDto.getCurrentLat(),
                nearbyDto.getSearchRadius(),
                nearbyDto.getLimitation(),
                nearbyDto.getUnit());
    }

    @Override
    @Transactional
    public List<JobNearbyDto> findJobsNearBy(MapSearchNearbyDto nearbyDto) {
        String categoriesStr = Arrays.toString(nearbyDto.getCategories().toArray()).replace("[","").replace("]","");
        return jobRepository.getJobsNearBy(nearbyDto.getId(),
                nearbyDto.getCurrentLng(),
                nearbyDto.getCurrentLat(),
                nearbyDto.getSearchRadius(),
                nearbyDto.getLimitation(),
                nearbyDto.getUnit(),
                categoriesStr,
                nearbyDto.getPriceMax(),
                nearbyDto.getPriceMin());
    }
}
