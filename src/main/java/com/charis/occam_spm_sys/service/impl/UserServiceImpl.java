package com.charis.occam_spm_sys.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.mapper.UserMapper;
import com.charis.occam_spm_sys.model.dto.UserQueryDTO;
import com.charis.occam_spm_sys.model.vo.UserInfoVO;
import com.charis.occam_spm_sys.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Override
	public User findUserByEmail(String email) {
		return this.lambdaQuery()
					.eq(User::getEmail, email)
					.one();
	}

	@Override
	public List<User> findUsersByEmail(List<String> emails) {
		
		if (CollectionUtils.isEmpty(emails)) return Collections.emptyList();
	    
		Set<String> emailSet = emails.stream()
									.filter(StringUtils::hasText)
									.collect(Collectors.toSet());
		
		if(emailSet.isEmpty()) return Collections.emptyList();
		
		return this.lambdaQuery()
					.in(User::getEmail, emailSet)
					.list();
	}
	
	@Override
	public UserInfoVO getUserById(Long userId) {
		User user = this.getById(userId);
		
		if(user==null) return null;
		
		UserInfoVO vo = new UserInfoVO();
		BeanUtils.copyProperties(user, vo);
		return vo;
	}
	@Override
	public Boolean patchUserByQuery(UserQueryDTO query) {
		LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
	    
	    updateWrapper.eq(User::getId, query.getId());
	    updateWrapper.set(StringUtils.hasText(query.getName()), User::getName, query.getName())
	                 .set(StringUtils.hasText(query.getEmail()), User::getEmail, query.getEmail())
	                 .set(StringUtils.hasText(query.getSchool()), User::getSchool, query.getSchool())
	                 .set(StringUtils.hasText(query.getDepartment()), User::getDepartment, query.getDepartment())
	                 .set(query.getRole() != null, User::getRole, query.getRole())
	                 .set(StringUtils.hasText(query.getNo()), User::getNo, query.getNo())
	                 .set(query.getSex() != null, User::getSex, query.getSex())
	                 .set(query.getStatus() != null, User::getStatus, query.getStatus());
		return this.update(updateWrapper);
	}

	@Override
	public List<UserInfoVO> getUsersByQuery(UserQueryDTO query) {
		var queryWrapper = new LambdaQueryWrapper<User>();
		queryWrapper.eq(query.getId() != null, User::getId, query.getId())
				.like(StringUtils.hasText(query.getName()), User::getName, query.getName())
				.like(StringUtils.hasText(query.getEmail()), User::getEmail, query.getEmail())
				.like(StringUtils.hasText(query.getSchool()), User::getSchool, query.getSchool())
				.like(StringUtils.hasText(query.getDepartment()), User::getDepartment, query.getDepartment())
				.eq(query.getRole() != null, User::getRole, query.getRole())
				.eq(StringUtils.hasText(query.getNo()), User::getNo, query.getNo())
				.eq(query.getSex() != null, User::getSex, query.getSex())
				.eq(query.getStatus() != null, User::getStatus, query.getStatus()).orderByDesc(User::getId);
		List<User> users = this.list(queryWrapper);
		List<UserInfoVO> userVOs = users.stream().map(u -> {
			UserInfoVO vo = new UserInfoVO();
			BeanUtils.copyProperties(u, vo);
			return vo;
		}).collect(Collectors.toList());
		
		return userVOs;
	}


	

//	@Override
//	public Boolean existsById(Long userId) {
//		
//		return null;
//	}
}
