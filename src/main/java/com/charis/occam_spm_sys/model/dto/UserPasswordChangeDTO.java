package com.charis.occam_spm_sys.model.dto;

import lombok.Data;

@Data
public class UserPasswordChangeDTO {
	private Long userId;
	private String oldPwd;
	private String newPwd;
}
