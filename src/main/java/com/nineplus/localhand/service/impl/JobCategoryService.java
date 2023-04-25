package com.nineplus.localhand.service.impl;

import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.JobCategory;
import com.nineplus.localhand.repository.JobCategoryRepository;
import com.nineplus.localhand.utils.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobCategoryService {
    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    public List<JobCategory> findAll() throws LocalHandException {
        try{
            List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
            return jobCategoryList;
        } catch (Exception e){
            throw new LocalHandException(CommonConstants.MessageError.ER012, null);
        }

    }
}
