package com.charis.occam_spm_sys.service;

import java.util.List;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.model.dto.UserPasswordChangeDTO;

public interface AuthService {
	public Result login(User user);
	public Result register(User user);
	public Result registerBatch(List<User> user);
	public Result changePassword(UserPasswordChangeDTO dto);
}
