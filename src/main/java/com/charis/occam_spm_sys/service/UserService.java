package com.charis.occam_spm_sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.vo.UserInfoVO;


public interface UserService extends IService<User>{
	public Boolean patch(Long userId, Map<String, Object> params);
	public List<User> findUserByEmail(String email);
	public UserInfoVO getUserInfoVO(Long userId);
	public List<UserInfoVO> getUserInfoVOList();
}
