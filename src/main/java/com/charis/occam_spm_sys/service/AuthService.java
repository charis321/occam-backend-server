package com.charis.occam_spm_sys.service;

import java.util.List;
import java.util.Map;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.model.dto.UserAuthDTO;
import com.charis.occam_spm_sys.model.dto.UserPasswordChangeDTO;

public interface AuthService {
	public UserAuthDTO login(User user);
	public void register(User user);
	public void registerBatch(List<User> user);
	public void changePassword(UserPasswordChangeDTO dto);
}
