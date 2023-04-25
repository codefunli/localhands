package com.nineplus.localhand.service;

import com.nineplus.localhand.dto.AdminResetReq;
import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.Admin;

public interface AdminServices {

    UserDto resetUserPassword(AdminResetReq adminResetReq) throws LocalHandException;

    UserDto deleteUser(Long id) throws LocalHandException;
}
