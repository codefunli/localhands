package com.nineplus.localhand.controller;

import com.nineplus.localhand.dto.UserUpdateDto;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.service.UserProfileService;
import com.nineplus.localhand.utils.CommonConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping(value = "/profile")
@Api(tags = "user-profile-api")
@CrossOrigin
public class UserProfileController extends BaseController{

    @Autowired
    private UserProfileService userProfileService;

    @ApiOperation("Update driver license")
    @PutMapping(value = "/driver-license")
    private ResponseEntity<?> updateLicense(@RequestPart UserUpdateDto userUpdateDto,
                                         @RequestPart List<MultipartFile> driverLicense
                                          ){
        try{
            UserUpdateDto dto = userProfileService.updateLicense(userUpdateDto, driverLicense);
            return success(CommonConstants.MessageSuccess.SC003,dto,null);
        } catch (LocalHandException e){
            return failed(e.getMsgCode(), null);
        }
    }

    @ApiOperation("Update payment")
    @PutMapping(value = "/update-payment")
    private ResponseEntity<?> updatePayment(@RequestBody UserUpdateDto userUpdateDto){
        try{
            UserUpdateDto dto = userProfileService.updatePayment(userUpdateDto);
            return success(CommonConstants.MessageSuccess.SC003,dto,null);
        } catch (LocalHandException e){
            return failed(e.getMsgCode(), null);
        }
    }

    @ApiOperation("Update job category")
    @PutMapping(value = "/update-category")
    private ResponseEntity<?> updateCategory(@RequestBody UserUpdateDto userUpdateDto){
        try{
            UserUpdateDto dto = userProfileService.updateCategory(userUpdateDto);
            return success(CommonConstants.MessageSuccess.SC003,dto,null);
        } catch (LocalHandException e){
            return failed(e.getMsgCode(), null);
        }
    }

    @ApiOperation("Update profile info")
    @PutMapping(value = "/update-info")
    private ResponseEntity<?> updateInfo(@RequestPart UserUpdateDto userUpdateDto, @RequestPart MultipartFile avatar){
        try{
            UserUpdateDto dto = userProfileService.updateInfo(userUpdateDto, avatar);
            return success(CommonConstants.MessageSuccess.SC003,dto,null);
        } catch (LocalHandException e){
            return failed(e.getMsgCode(), null);
        }
    }
}
