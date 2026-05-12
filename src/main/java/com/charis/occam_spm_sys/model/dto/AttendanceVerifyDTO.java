package com.charis.occam_spm_sys.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true) 
public class AttendanceVerifyDTO extends AttendanceDTO{
	
	@NotBlank(message="點名碼 不可為空")
	private String code;
}
