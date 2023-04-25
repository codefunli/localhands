package com.nineplus.localhand.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResetPasswordReqDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6795176570305696156L;

	@JsonProperty("password")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^`<>&+=\"!ºª·#~%&'¿¡€,:;*/+-.=_\\[\\]\\(\\)\\|\\_\\?\\\\])(?=\\S+$).{8,100}$", message = "Enter at least 8 characters, including uppercase(s), lowercase(s), digit(s) and special character(s).")
	private String password;


}
