package com.nineplus.localhand.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nineplus.localhand.dto.ForgotPasswordReqDto;
import com.nineplus.localhand.dto.ResetPasswordReqDto;
import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.User;
import com.nineplus.localhand.service.AuthServices;
import com.nineplus.localhand.service.UserService;
import com.nineplus.localhand.utils.CommonConstants;
import com.nineplus.localhand.utils.MailService;
import com.nineplus.localhand.utils.TokenUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService implements AuthServices {
    @Value("${app.password.secretKey}")
    private String SECRET_KEY;

    @Value("${url.origin}")
    private String url;

    @Value("${app.password.expiration}")
    private int EXPIRATION;

    @Autowired
    private MailService mailService;

    @Autowired
    UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public boolean forgotPassword(ForgotPasswordReqDto forgotPasswordReqDto) throws LocalHandException {
        String email = forgotPasswordReqDto.getEmail().trim();
        User user = userService.getUserByEmail(email);
        if (ObjectUtils.isEmpty(user)){
            throw new LocalHandException(CommonConstants.MessageError.ER010, new Object[]{"User"});
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
            String token = JWT.create().withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION * 1000L))
                    .sign(algorithm);
            String resetUrl = url + "/api/v1/auth/reset-password/" + token;
            mailService.sendResetPwEmail(user, "Reset Password", resetUrl);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDto resetPassword(ResetPasswordReqDto resetPasswordReqDto, String token) throws LocalHandException {
        try{
            DecodedJWT decodedJWT = tokenUtils.decodedPasswordToken(token);
            User user = userService.getUserByEmail(decodedJWT.getSubject());
            if (ObjectUtils.isEmpty(user)) {
                throw new LocalHandException(CommonConstants.MessageError.ER012, null);
            }
            String newPassword = resetPasswordReqDto.getPassword();
            UserDto userDto = userService.updatePassword(user, newPassword);
            return userDto;
        } catch (Exception e){
            throw new LocalHandException(CommonConstants.MessageError.ER015, null);
        }
    }
}
