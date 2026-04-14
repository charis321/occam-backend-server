package com.charis.occam_spm_sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.charis.occam_spm_sys.common.JWTUtil;
import com.charis.occam_spm_sys.common.OCPasswordEncoder;
import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.model.dto.UserPasswordChangeDTO;
import com.charis.occam_spm_sys.service.AuthService;
import com.charis.occam_spm_sys.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserService userService;
	@Autowired
	private OCPasswordEncoder ocPasswordEncoder;

	private final JWTUtil jwtUtil;
	private final AuthenticationManager authenticationManager;

	public AuthServiceImpl(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Result login(User user) {
		List<User> userList = userService.findUserByEmail(user.getEmail());
		if (userList.size() == 0)
			return Result.fail("此信箱並未註冊!");
		if (userList.size() != 1)
			return Result.fail("系統錯誤!");
		if (userList.get(0).getStatus() == 1)
			return Result.fail("此用戶已停用!");

		if (!ocPasswordEncoder.matches(user.getPassword(), userList.get(0).getPassword())) {
			return Result.fail("密碼錯誤!");
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

		if (authentication.isAuthenticated()) {
			String token = jwtUtil.generateToken(userList.get(0));

			Map<String, Object> userData = new HashMap<>();
			userData.put("id", userList.get(0).getId().toString());
			userData.put("name", userList.get(0).getName());
			userData.put("role", userList.get(0).getRole());
			userData.put("token", token);

			return Result.success("登入成功!", userData);
		} else {
			throw new RuntimeException("Invalid Access");
		}
	}

	@Override
	public Result register(User user) {
		List<User> userList = userService.findUserByEmail(user.getEmail());
		if (userList.size() > 0)
			return Result.fail("此信箱已註冊!");

		String password = ocPasswordEncoder.encode(user.getPassword());
		user.setPassword(password);

		return userService.save(user) ? Result.success("註冊成功!") : Result.fail();
	}

	@Override
	public Result registerBatch(List<User> userList) {
		log.debug("正在批量註冊用戶");
		List<User> userInvaildList = new ArrayList<>();
		List<User> userListWithPwd = userList.stream()
												   .map(user->{
													   List<User> result= userService.findUserByEmail(user.getEmail());
														if (result.size() > 0) {
															userInvaildList.add(user);
															return null;
														}
														String password = ocPasswordEncoder.encode(user.getNo());
														user.setPassword(password);
														return user;
													})
												   .collect(Collectors.toList());

		if(userInvaildList.size()>0) {
			return Result.fail(409, "批量註冊失敗", userInvaildList);
		}
		return userService.saveBatch(userListWithPwd) ? Result.success("註冊成功!") : Result.fail();
	}
	
	@Override
	public Result changePassword(UserPasswordChangeDTO dto) {
		log.debug("正在處理用戶更改密碼，User Id: {}", dto.getUserId());
		User user  = userService.getById(dto.getUserId());
		if(user==null) return Result.fail("更改密碼失敗: 用戶id不存在!");
		if (!ocPasswordEncoder.matches(dto.getOldPwd(), user.getPassword())){
			return Result.fail("更改密碼失敗: 舊密碼錯誤!");
		}
		
		var updateWrapper = new LambdaUpdateWrapper<User>().eq(User::getId, user.getId());
		updateWrapper.set(User::getPassword, ocPasswordEncoder.encode(dto.getNewPwd()));
		
		return userService.update(updateWrapper)?Result.success("更新密碼成功"):Result.fail("更新密碼失敗");
	}
}
