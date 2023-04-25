package com.nineplus.localhand.controller;

import com.nineplus.localhand.dto.JobRatingDto;
import com.nineplus.localhand.enum_class.RatingType;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.service.IJobRatingServices;
import com.nineplus.localhand.utils.CommonConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rating")
@Api(tags = "job-rating-api")
public class JobRatingController extends BaseController{

    @Autowired
    private IJobRatingServices jobRatingServices;

    @ApiOperation("Rating local")
    @PostMapping("/local")
    public ResponseEntity<? extends Object> ratingLocal(@RequestBody JobRatingDto ratingDto) {
        JobRatingDto ratingDtoRes = null;
        try {
            ratingDtoRes = jobRatingServices.ratingJob(ratingDto, RatingType.LOCAL);
            return success(CommonConstants.MessageSuccess.SC003, ratingDtoRes, null);
        } catch (LocalHandException e) {
            return failed(e.getMsgCode(),null);
        }
    }

    @ApiOperation("Rating helper")
    @PostMapping("/helper")
    public ResponseEntity<? extends Object> ratingHelper(@RequestBody JobRatingDto ratingDto) {
        JobRatingDto ratingDtoRes = null;
        try {
            ratingDtoRes = jobRatingServices.ratingJob(ratingDto, RatingType.HELPER);
            return success(CommonConstants.MessageSuccess.SC003, ratingDtoRes, null);
        } catch (LocalHandException e) {
            return failed(e.getMsgCode(),null);
        }
    }
}
