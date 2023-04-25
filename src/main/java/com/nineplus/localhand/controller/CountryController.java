package com.nineplus.localhand.controller;

import com.nineplus.localhand.model.CommonCountry;
import com.nineplus.localhand.model.CommonState;
import com.nineplus.localhand.service.impl.CountryService;
import com.nineplus.localhand.service.impl.StateService;
import com.nineplus.localhand.utils.CommonConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/location")
@Api(tags = "location-api")
@CrossOrigin
public class CountryController extends BaseController{

    @Autowired
    private CountryService countryService;

    @Autowired
    private StateService stateService;

    @ApiOperation("Get countries")
    @GetMapping("/countries")
    public ResponseEntity<? extends Object> getAllCountries() {
        List<CommonCountry> countryList = countryService.getAllCountry();
        return success(CommonConstants.MessageSuccess.SC001, countryList, null);
    }

    @ApiOperation("Get states")
    @GetMapping("/states")
    public ResponseEntity<? extends Object> getAllStates() {
        List<CommonState> stateList = stateService.getAllState();
        return success(CommonConstants.MessageSuccess.SC001, stateList, null);
    }
}
