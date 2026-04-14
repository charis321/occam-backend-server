package com.charis.occam_spm_sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.model.dto.UserQueryDTO;
import com.charis.occam_spm_sys.model.vo.UserInfoVO;


public interface UserService extends IService<User>{
	
	public List<User> getUsersByQuery(UserQueryDTO query);
	public List<User> findUserByEmail(String email);
//	public Boolean existsByEmail(Long Email);
	
	public Boolean patch(Long userId, Map<String, Object> params);
	
	public UserInfoVO getUserInfoVO(Long userId);
	public List<UserInfoVO> getUserInfoVOList();
	
}
