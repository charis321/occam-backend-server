package com.charis.occam_spm_sys.service;

import java.util.List;

import com.charis.occam_spm_sys.model.dto.UserAuthDTO;
import com.charis.occam_spm_sys.model.dto.UserLoginDTO;
import com.charis.occam_spm_sys.model.dto.UserPasswordChangeDTO;
import com.charis.occam_spm_sys.model.dto.UserRegisterDTO;

public interface AuthService {
	public UserAuthDTO login(UserLoginDTO user);
	public void register(UserRegisterDTO user);
	public void registerBatch(List<UserRegisterDTO> user);
	public void changePassword(UserPasswordChangeDTO dto);
}
