package com.nineplus.localhand.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
public class ForgotPasswordReqDto extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3586819945558785378L;

	@NotBlank(message = "Enter your email.")
	@Email(message = "Invalid email.")
	private String email;
}
