package com.nineplus.localhand.service.impl;

import com.nineplus.localhand.dto.JobRatingDto;
import com.nineplus.localhand.enum_class.JobStatus;
import com.nineplus.localhand.enum_class.RatingType;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.Job;
import com.nineplus.localhand.model.JobRating;
import com.nineplus.localhand.model.User;
import com.nineplus.localhand.repository.JobRatingRepository;
import com.nineplus.localhand.repository.JobRepository;
import com.nineplus.localhand.repository.UserRepository;
import com.nineplus.localhand.service.IJobRatingServices;
import com.nineplus.localhand.utils.CommonConstants;
import com.nineplus.localhand.utils.UserAuthUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JobRatingServices implements IJobRatingServices {

    @Autowired
    private UserAuthUtils userAuthUtils;

    @Autowired
    private JobRatingRepository jobRatingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Override
    public JobRatingDto ratingJob(JobRatingDto ratingDto, RatingType type) throws LocalHandException {
        try{
            validateInput(ratingDto, type);
            JobRating rating = new JobRating();
            BeanUtils.copyProperties(ratingDto, rating);
            rating.setRatingType(type);
            rating.setRatingDateRated(Instant.now());
            rating.setRatingDateApproved(Instant.now());
            rating.setRatingStatus(true);
            rating.setSysCreatedAt(Instant.now());
            rating.setSysUpdatedAt(Instant.now());
            rating.setSysCreatedBy(userAuthUtils.getUserInfoFromReq().getUsername());
            rating.setSysUpdatedBy(userAuthUtils.getUserInfoFromReq().getUsername());
            if (ObjectUtils.isNotEmpty(ratingDto.getTip())){
                // handel tip
            }
            rating = jobRatingRepository.save(rating);
            BeanUtils.copyProperties(rating, ratingDto);
            return ratingDto;
        } catch (LocalHandException e){
            throw new LocalHandException(e.getMsgCode(), null);
        }
    }

    public void validateInput(JobRatingDto ratingDto, RatingType type) throws LocalHandException{
        if (ObjectUtils.isEmpty(ratingDto)){
            throw new LocalHandException(CommonConstants.MessageError.ER015, null);
        }
        if (ObjectUtils.isEmpty(ratingDto.getUserId()) || ObjectUtils.isEmpty(ratingDto.getTaskId())){
            throw new LocalHandException(CommonConstants.MessageError.ER003, null);
        }
        User user = userRepository.findById(ratingDto.getUserId()).orElse(null);
        Job job = jobRepository.findById(ratingDto.getTaskId()).orElse(null);
        if (ObjectUtils.isEmpty(user) || ObjectUtils.isEmpty(job)){
            throw new LocalHandException(CommonConstants.MessageError.ER021, null);
        }
        if (!JobStatus.DONE.equals(job.getJobStatus())){
            throw new LocalHandException(CommonConstants.MessageError.ER026, null);
        }
        if (RatingType.LOCAL.equals(type)){
            if (!job.getLocalUserId().equals(ratingDto.getUserId())){
                throw new LocalHandException(CommonConstants.MessageError.ER003, null);
            }
        } else {
            if (!job.getHelperUserId().equals(ratingDto.getUserId())){
                throw new LocalHandException(CommonConstants.MessageError.ER003, null);
            }
        }
    }
}
