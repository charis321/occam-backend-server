package com.charis.occam_spm_sys.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {
	
	@NotBlank(message="電子信箱 不可為空")
	@Email(message = "電子信箱 格式不正確")
	private String email;
	@NotBlank(message="密碼 不可為空")
	private String password;
	
}
