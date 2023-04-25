package com.nineplus.localhand.controller;

import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.CommonCountry;
import com.nineplus.localhand.model.JobCategory;
import com.nineplus.localhand.service.impl.JobCategoryService;
import com.nineplus.localhand.utils.CommonConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/category")
@Api(tags = "job-category-api")
@CrossOrigin
public class JobCategoryController extends BaseController{

    @Autowired
    private JobCategoryService jobCategoryService;

    @ApiOperation("Get categories")
    @GetMapping("/list")
    public ResponseEntity<? extends Object> getAllCategory() {
        try {
            List<JobCategory> categories = jobCategoryService.findAll();
            return success(CommonConstants.MessageSuccess.SC001, categories, null);
        } catch (LocalHandException e) {
            return failed(e.getMsgCode(), null);
        }
    }
}
