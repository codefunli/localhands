package com.nineplus.localhand.controller;

import com.nineplus.localhand.dto.MapSearchNearbyDto;
import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.dto.jpa_interface.JobNearbyDto;
import com.nineplus.localhand.dto.jpa_interface.UserNearbyDto;
import com.nineplus.localhand.service.MapServices;
import com.nineplus.localhand.utils.CommonConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/map")
@Api(tags = "map-api")
public class MapController extends BaseController{

    @Autowired
    MapServices mapServices;

    @ApiOperation("Get helpers in radius")
    @PostMapping("/users")
    private ResponseEntity<?> getUsersNearby(@RequestBody MapSearchNearbyDto nearbyDto) {
        try {
            List<UserNearbyDto> nearbyDtos = mapServices.findUsersNearby(nearbyDto);
            return success(CommonConstants.MessageSuccess.SC001,nearbyDtos,null);
        }catch (Exception e) {
            return failed(e.getMessage(),null);
        }
    }

    @ApiOperation("Get jobs in radius")
    @PostMapping("/jobs")
    private ResponseEntity<?> getJobsNearby(@RequestBody MapSearchNearbyDto nearbyDto) {
        try {
            List<JobNearbyDto> nearbyDtos = mapServices.findJobsNearBy(nearbyDto);
            return success(CommonConstants.MessageSuccess.SC001,nearbyDtos,null);
        }catch (Exception e) {
            return failed(e.getMessage(),null);
        }
    }
}
