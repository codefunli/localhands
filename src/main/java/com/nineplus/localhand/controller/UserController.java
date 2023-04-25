package com.nineplus.localhand.controller;

import com.nineplus.localhand.dto.AuthenDto;
import com.nineplus.localhand.dto.UserDetailDto;
import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.service.UserService;
import com.nineplus.localhand.utils.CommonConstants;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/user")
@Api(tags = "user-api")
@CrossOrigin
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    private ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()){
                return failedWithError(CommonConstants.MessageError.ER015, bindingResult.getFieldErrors().toArray(), null);
            }
            UserDto user = userService.validationAndSendOtpUser(userDto);
            return success(CommonConstants.MessageSuccess.SC005,user,null);
        }catch (LocalHandException e) {
            return failed(e.getMsgCode(),null);
        }
    }

    @PostMapping(value = "/check")
    private ResponseEntity<?> checkExistOtp(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()){
                return failedWithError(CommonConstants.MessageError.ER015, bindingResult.getFieldErrors().toArray(), null);
            }
            AuthenDto user = userService.sendOtp(userDto);
            return success(CommonConstants.MessageSuccess.SC001,user,null);
        }catch (LocalHandException e) {
            return failed(e.getMsgCode(),null);
        }
    }

    @GetMapping(value = "/detail/{id}")
    private ResponseEntity<?> getUserDetail(@PathVariable Long id) {
        try {

            UserDetailDto userDetailDto = userService.getUserDetail(id);
            return success(CommonConstants.MessageSuccess.SC001,userDetailDto,null);
        }catch (LocalHandException e) {
            return failed(e.getMsgCode(),null);
        }
    }

    @GetMapping(value = "/my-info")
    private ResponseEntity<?> getMyInfo() {
        try {
            UserDetailDto userDetailDto = userService.getMyInfo();
            return success(CommonConstants.MessageSuccess.SC001,userDetailDto,null);
        }catch (LocalHandException e) {
            return failed(e.getMsgCode(),null);
        }
    }
}
