package com.nineplus.localhand.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChangePasswordReqDto extends BaseDto {

	private static final long serialVersionUID = 2489379081966449543L;

	@NotBlank(message = "Enter your current password")
	@JsonProperty("currentPassword")
	private String currentPassword;

	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^`<>&+=\"!ºª·#~%&'¿¡€,:;*/+-.=_\\[\\]\\(\\)\\|\\_\\?\\\\])(?=\\S+$).{8,100}$", message = "Enter at least characters, including uppercase(s), lowercase(s), digit(s) and special character(s).")
	@JsonProperty("newPassword")
	private String newPassword;

}
