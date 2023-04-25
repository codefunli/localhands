package com.nineplus.localhand.controller;

import com.nineplus.localhand.dto.JobDto;
import com.nineplus.localhand.dto.JobProgressDto;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.service.JobServices;
import com.nineplus.localhand.utils.CommonConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/job")
@Api(tags = "job-api")
public class JobController extends BaseController {

    @Autowired
    private JobServices jobServices;

    @ApiOperation("Create new job")
    @PostMapping("/create")
    public ResponseEntity<? extends Object> createNewJob(@RequestPart JobDto jobDto, List<MultipartFile> files) {
        JobDto jobDtoRe = null;
        try {
            jobDtoRe = jobServices.createJob(jobDto, files);
            return success(CommonConstants.MessageSuccess.SC001, jobDtoRe, null);
        } catch (LocalHandException e) {
            return failed(e.getMsgCode(),null);
        }
    }

    @ApiOperation("Update job")
    @PostMapping("/update")
    public ResponseEntity<? extends Object> updateJob(@RequestBody JobDto jobDto) {
        JobDto jobDtoRe = null;
        try {
            jobDtoRe = jobServices.updateJob(jobDto);
        } catch (Exception e) {
            return failed(e.getMessage(),null);
        }
        return success(CommonConstants.MessageSuccess.SC001, jobDtoRe, null);
    }

    @ApiOperation("Accept job")
    @PostMapping("/accept")
    public ResponseEntity<? extends Object> acceptJob(@RequestBody JobProgressDto jobDto) {
        JobDto jobDtoRe = null;
        try {
            jobDtoRe = jobServices.acceptJob(jobDto);
        } catch (Exception e) {
            return failed(e.getMessage(),null);
        }
        return success(CommonConstants.MessageSuccess.SC001, jobDtoRe, null);
    }

    @ApiOperation("Cancel job")
    @PostMapping("/cancel")
    public ResponseEntity<? extends Object> cancelJob(@RequestBody JobProgressDto jobDto) {
        JobDto jobDtoRe = null;
        try {
            jobDtoRe = jobServices.cancelJob(jobDto);
        } catch (Exception e) {
            return failed(e.getMessage(),null);
        }
        return success(CommonConstants.MessageSuccess.SC001, jobDtoRe, null);
    }

    @ApiOperation("Done job")
    @PostMapping("/done")
    public ResponseEntity<? extends Object> doneJob(@RequestBody JobProgressDto jobDto) {
        JobDto jobDtoRe = null;
        try {
            jobDtoRe = jobServices.doneJob(jobDto);
        } catch (Exception e) {
            return failed(e.getMessage(),null);
        }
        return success(CommonConstants.MessageSuccess.SC001, jobDtoRe, null);
    }

    @ApiOperation("Get job detail")
    @GetMapping("/detail/{id}")
    public ResponseEntity<? extends Object> getDetail(@PathVariable Long id) {
        try{
            JobDto job = jobServices.getDetail(id);
            return success(CommonConstants.MessageSuccess.SC001, job, null);
        } catch (LocalHandException e){
            return failed(e.getMsgCode(), null);
        }
    }

}
