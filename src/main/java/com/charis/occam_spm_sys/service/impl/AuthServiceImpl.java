package com.charis.occam_spm_sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
	private final String DEFAULT_PWD = "123456";

	public AuthServiceImpl(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Result login(User user) {
		User existingUser = userService.findUserByEmail(user.getEmail());
		if (existingUser == null)
			return Result.fail("帳號或密碼錯誤!");
		if (existingUser.getStatus() == 1)
			return Result.fail("此用戶已停用!");

		if (!ocPasswordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
			return Result.fail("帳號或密碼錯誤!");
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

		if (authentication.isAuthenticated()) {
			String token = jwtUtil.generateToken(existingUser);

			Map<String, Object> userData = new HashMap<>();
			userData.put("id", existingUser.getId().toString());
			userData.put("name", existingUser.getName());
			userData.put("role", existingUser.getRole());
			userData.put("token", token);

			return Result.success("登入成功!", userData);
		} else {
			throw new RuntimeException("Invalid Access");
		}
	}

	@Override
	public Result register(User user) {
		log.debug("正在註冊用戶");
		User existingUser = userService.findUserByEmail(user.getEmail());
		if (existingUser != null)
			return Result.fail("註冊失敗!");
	
		String password = ocPasswordEncoder.encode(user.getPassword());
		user.setPassword(password);

		return userService.save(user) ? Result.success("註冊成功!") : Result.fail("註冊失敗!");
	}

	@Override
	@Transactional
	public Result registerBatch(List<User> users) {
		log.debug("正在批量註冊用戶");		
		if(CollectionUtils.isEmpty(users)) return Result.fail("批量註冊失敗: 資料不符合規範!");
		
		List<String> emails = users.stream()
									.map(User::getEmail)
									.distinct()
									.collect(Collectors.toList());
		List<User> existingUsers = userService.findUsersByEmail(emails);
		Set<String> existingEmailSet = existingUsers.stream()
													.map(User::getEmail)
													.collect(Collectors.toSet());
				
		List<User> userListWithPwd = new ArrayList<>();
		List<User> invalidUserList = new ArrayList<>();
		
		for(User user : users) {
			if(existingEmailSet.contains(user.getEmail())){
				invalidUserList.add(user);
			}else {
				String password = generateUserPassword(user);
				user.setPassword(ocPasswordEncoder.encode(password));
				userListWithPwd.add(user);
			}
			
		}
		
		if(!invalidUserList.isEmpty()) {
			return Result.fail(409, "批量註冊失敗", invalidUserList);
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
	
	private String generateUserPassword(User user) {	
		String password = user.getPassword();
		if(StringUtils.hasText(password)) {
			return password;
		}else if(StringUtils.hasText(user.getNo())){
			return user.getNo();
		}else {
			return DEFAULT_PWD;
		}
	}
}
