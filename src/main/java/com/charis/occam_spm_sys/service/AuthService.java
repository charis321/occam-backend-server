package com.charis.occam_spm_sys.service;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.User;

public interface AuthService {
	public Result login(User user);
	public Result register(User user);
}
