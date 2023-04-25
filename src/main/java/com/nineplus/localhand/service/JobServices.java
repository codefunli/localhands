package com.nineplus.localhand.service;

import com.nineplus.localhand.dto.JobDto;
import com.nineplus.localhand.dto.JobProgressDto;
import com.nineplus.localhand.exceptions.LocalHandException;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface JobServices {
    JobDto createJob(JobDto jobDto, List<MultipartFile> files) throws LocalHandException;

    JobDto updateJob(JobDto jobDto) throws LocalHandException;

    JobDto getDetail(Long id) throws LocalHandException;

    JobDto cancelJob(JobProgressDto jobDto) throws LocalHandException;

    JobDto acceptJob(JobProgressDto jobDto) throws LocalHandException;

    JobDto doneJob(JobProgressDto jobDto) throws LocalHandException;
}

