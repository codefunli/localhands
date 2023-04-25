package com.nineplus.localhand.service;

import com.nineplus.localhand.dto.JobRatingDto;
import com.nineplus.localhand.enum_class.RatingType;
import com.nineplus.localhand.exceptions.LocalHandException;

public interface IJobRatingServices {

    JobRatingDto ratingJob(JobRatingDto ratingDto, RatingType type) throws LocalHandException;
}
