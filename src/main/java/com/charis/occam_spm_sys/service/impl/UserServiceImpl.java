package com.charis.occam_spm_sys.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.mapper.UserMapper;
import com.charis.occam_spm_sys.model.dto.UserQueryDTO;
import com.charis.occam_spm_sys.model.vo.UserInfoVO;
import com.charis.occam_spm_sys.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Override
	public List<User> findUserByEmail(String email) {
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
		lambdaQueryWrapper.eq(User::getEmail, email);
		List<User> res = this.list(lambdaQueryWrapper);
		return res;
	}

	@Override
	public UserInfoVO getUserInfoVO(Long userId) {
		UserInfoVO vo = new UserInfoVO();
		User user = this.getById(userId);
		BeanUtils.copyProperties(user, vo);
		return vo;
	}

	@Override
	public List<UserInfoVO> getUserInfoVOList() {
		List<User> userList = this.list();
		List<UserInfoVO> userInfoVOList = userList.stream().map(user -> {
			UserInfoVO vo = getUserInfoVO(user.getId());
			return vo;
		}).collect(Collectors.toList());

		return userInfoVOList;
	}

	@Override
	public Boolean patch(Long userId, Map<String, Object> params) {
		UpdateWrapper<User> wrapper = new UpdateWrapper<>();
		wrapper.eq("id", userId);
		params.forEach((key, value) -> {
			if (value != null) {
				wrapper.set(key, value);
			}
		});
		return this.update(wrapper);
	}

	@Override
	public List<User> getUsersByQuery(UserQueryDTO query) {
		var queryWrapper = new LambdaQueryWrapper<User>();
		queryWrapper.eq(query.getId() != null, User::getId, query.getId())
				.like(StringUtils.hasText(query.getName()), User::getName, query.getName())
				.like(StringUtils.hasText(query.getEmail()), User::getEmail, query.getEmail())
				.like(StringUtils.hasText(query.getSchool()), User::getSchool, query.getSchool())
				.like(StringUtils.hasText(query.getDepartment()), User::getDepartment, query.getDepartment())
				.eq(query.getRole() != null, User::getRole, query.getRole())
				.eq(StringUtils.hasText(query.getNo()), User::getNo, query.getNo())
				.eq(query.getSex() != null, User::getSex, query.getSex())
				.eq(query.getStatus() != null, User::getStatus, query.getStatus())
				.orderByDesc(User::getId);
		return this.list(queryWrapper);
	}

//	@Override
//	public Boolean existsById(Long userId) {
//		
//		return null;
//	}
}
