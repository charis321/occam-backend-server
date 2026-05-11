package com.charis.occam_spm_sys.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.model.dto.UserQueryDTO;
import com.charis.occam_spm_sys.model.vo.UserInfoVO;


public interface UserService extends IService<User>{
	public UserInfoVO getUserById(Long userId);
	public List<UserInfoVO> getUsersByQuery(UserQueryDTO query);
	public User findUserByEmail(String email);
	public List<User> findUsersByEmail(List<String> emails);
//	public Boolean existsByEmail(Long Email);
	
	public Boolean patchUserByQuery(UserQueryDTO query);
	public void uploadAvatar(Long userId, MultipartFile file);
	
//	public List<UserInfoVO> getUserInfoVOList();
	
}
