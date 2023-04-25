package com.nineplus.localhand.service;

import com.nineplus.localhand.dto.ForgotPasswordReqDto;
import com.nineplus.localhand.dto.ResetPasswordReqDto;
import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.exceptions.LocalHandException;
import org.springframework.stereotype.Service;

public interface AuthServices {
    boolean forgotPassword(ForgotPasswordReqDto forgotPasswordReqDto) throws LocalHandException;

    UserDto resetPassword(ResetPasswordReqDto resetPasswordReqDto, String token) throws LocalHandException;
}
