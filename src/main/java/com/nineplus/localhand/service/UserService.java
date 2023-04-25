package com.nineplus.localhand.service;

import com.nineplus.localhand.dto.AuthenDto;
import com.nineplus.localhand.dto.UserDetailDto;
import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);

    UserDto validationAndSendOtpUser(UserDto userDto) throws LocalHandException;

    AuthenDto sendOtp(UserDto userDto) throws LocalHandException;

    UserDto updatePassword(User currentUser, String newPassword) throws LocalHandException;

    User getUserByEmail(String email);

//    void updateUserLocation(LocationMessage locationMessage);

    UserDetailDto getUserDetail(Long userId) throws LocalHandException;

    UserDetailDto getMyInfo() throws LocalHandException;
}
