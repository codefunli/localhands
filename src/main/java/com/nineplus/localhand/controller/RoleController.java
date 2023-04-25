package com.nineplus.localhand.controller;

import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.CommonCountry;
import com.nineplus.localhand.model.Role;
import com.nineplus.localhand.service.impl.RoleService;
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
@RequestMapping(value = "/role")
@Api(tags = "role-api")
@CrossOrigin
public class RoleController extends BaseController{
    @Autowired
    private RoleService roleService;

    @ApiOperation("Get roles")
    @GetMapping("/list")
    public ResponseEntity<? extends Object> getAllRoles() {
        try{
            List<Role> roles = roleService.findAll();
            return success(CommonConstants.MessageSuccess.SC001, roles , null);
        } catch (LocalHandException e){
            return failed(e.getMsgCode(), null);
        }
    }
}
