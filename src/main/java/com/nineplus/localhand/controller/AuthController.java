package com.nineplus.localhand.controller;

import com.nineplus.localhand.dto.*;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.User;
import com.nineplus.localhand.service.AuthServices;
import com.nineplus.localhand.service.UserService;
import com.nineplus.localhand.utils.CommonConstants;
import com.nineplus.localhand.utils.UserAuthUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
//@RequestMapping("/auth")
@Api(tags = "auth-api")
@CrossOrigin
public class AuthController extends BaseController {

	@Autowired
	UserAuthUtils userAuthUtils;

	@Autowired
	UserService userService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private AuthServices authServices;

	@ApiOperation("Change password")
	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordReqDto changePasswordReqDto,
			BindingResult bindingResult) throws LocalHandException {
		try{
			UserAuthDetected userAuthRoleReq = userAuthUtils.getUserInfoFromReq();
			String username = userAuthRoleReq.getUsername();

			User currentUser = userService.getUserByUsername(username);

			if (bindingResult.hasErrors()) {
				return failedWithError(CommonConstants.MessageError.ER003, bindingResult.getFieldErrors().toArray(), null);
			}

			if (ObjectUtils.isEmpty(currentUser)) {
				return failed(CommonConstants.MessageError.ER010, new Object[]{"User"});
			}
			if (!bCryptPasswordEncoder.matches(changePasswordReqDto.getCurrentPassword(), currentUser.getPassword())) {
				return failedWithError(CommonConstants.MessageError.ER004, changePasswordReqDto.getCurrentPassword(),
						null);
			}
			String newPassword = changePasswordReqDto.getNewPassword();
			UserDto dto = userService.updatePassword(currentUser, newPassword);
			return success(CommonConstants.MessageSuccess.SC003, dto, null);
		} catch (Exception e){
			return failedWithError(CommonConstants.MessageError.ER013, null, null);
		}
	}

	@ApiOperation("Login.")
	@PostMapping("/login")
	@ResponseStatus(OK)
	public void fakeLogin(@RequestBody LogInDto dto) {}

	@ApiOperation("Logout.")
	@PostMapping("/logout")
	@ResponseStatus(OK)
	public void fakeLogout() {}

	@ApiOperation("Forgot password")
	@PostMapping("/forgot-password")
	public ResponseEntity< ? extends Object> forgotPassword(@Valid @RequestBody ForgotPasswordReqDto forgotPasswordReqDto,
															BindingResult bindingResult) throws LocalHandException{
		if (bindingResult.hasErrors()){
			return failedWithError(CommonConstants.MessageError.ER003, bindingResult.getFieldErrors().toArray(), null);
		}
		try{
			if (authServices.forgotPassword(forgotPasswordReqDto)){
				return success(CommonConstants.MessageSuccess.SC006, null, null);
			} else {
				return failed(CommonConstants.MessageError.ER003, null);
			}
		} catch (Exception e){
			return failed(CommonConstants.MessageError.ER003, null);
		}
	}

	@ApiOperation("Reset password")
	@PostMapping("/reset-password/{token}")
	public ResponseEntity<? extends Object> resetPassword(@PathVariable String token,
														  @Valid @RequestBody ResetPasswordReqDto resetPasswordReqDto,
														  BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return failedWithError(CommonConstants.MessageError.ER003, bindingResult.getFieldErrors().toArray(), null);
		}
		try{
			UserDto userDto = authServices.resetPassword(resetPasswordReqDto, token);
			return success(CommonConstants.MessageSuccess.SC003, userDto, null);
		} catch (Exception e){
			return failedWithError(CommonConstants.MessageError.ER013, resetPasswordReqDto, null);
		}

	}

}
