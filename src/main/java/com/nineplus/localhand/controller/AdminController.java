package com.nineplus.localhand.controller;

import com.nineplus.localhand.dto.AdminResetReq;
import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.service.AdminServices;
import com.nineplus.localhand.utils.CommonConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin")
@Api(tags = "admin-api")
//@ApiIgnore
@CrossOrigin
public class AdminController extends BaseController{

    @Autowired
    private AdminServices adminServices;

    @ApiOperation("Reset user password")
    @PutMapping(value = "/user-password")
    public ResponseEntity<?> resetUserPassword(@RequestBody AdminResetReq adminResetReq){
        try{
            UserDto userDto = adminServices.resetUserPassword(adminResetReq);
            return success(CommonConstants.MessageSuccess.SC003, userDto, null);
        } catch (LocalHandException e){
            return  failed(e.getMsgCode(), null);
        }
    }

    @ApiOperation("Delete user")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        try{
            UserDto userDto = adminServices.deleteUser(id);
            return success(CommonConstants.MessageSuccess.SC004, userDto, null);
        } catch (LocalHandException e){
            return  failed(e.getMsgCode(), null);
        }
    }
}
