package com.charis.occam_spm_sys.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterDTO {

	@NotNull(message = "用戶姓名 不能為空")
	private String name;
	@NotNull(message = "電子信箱 不能為空")
	private String email;
	
	@Min(value = 0, message = "性別 格式不正確")
    @Max(value = 2, message = "性別 格式不正確")
	private Integer sex = 0;
	
	private String password;
	
	private String no;
	
	private String school;
	private String department;
	
	@Min(value = 0, message = "身分 格式不正確")
    @Max(value = 2, message = "身分 格式不正確")
	private Integer role = 0;
	
}
