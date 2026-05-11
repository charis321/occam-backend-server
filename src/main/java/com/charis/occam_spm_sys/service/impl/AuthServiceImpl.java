package com.charis.occam_spm_sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.charis.occam_spm_sys.common.JWTUtil;
import com.charis.occam_spm_sys.common.OCPasswordEncoder;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.exception.BusinessException;
import com.charis.occam_spm_sys.model.dto.UserAuthDTO;
import com.charis.occam_spm_sys.model.dto.UserLoginDTO;
import com.charis.occam_spm_sys.model.dto.UserPasswordChangeDTO;
import com.charis.occam_spm_sys.model.dto.UserRegisterDTO;
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
	public UserAuthDTO login(UserLoginDTO dto) {
		log.debug("正在登入用戶 | Email: ",dto.getEmail());		
		User existingUser = userService.findUserByEmail(dto.getEmail());
		if (existingUser == null)
			throw new BusinessException(401, "帳號或密碼錯誤!");
		if (existingUser.getStatus() == 1)
			throw new BusinessException(403, "此用戶已停用!");

		if (!ocPasswordEncoder.matches(dto.getPassword(), existingUser.getPassword())) {
			throw new BusinessException(401, "帳號或密碼錯誤!");
		}
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
			if (authentication.isAuthenticated()) {
				String token = jwtUtil.generateToken(existingUser);

				return new UserAuthDTO(existingUser.getId(), 
										existingUser.getName(), 
										existingUser.getRole(),
										existingUser.getSex(), 
										existingUser.getAvatar(), token);
			}
		} catch (AuthenticationException e) {
			log.warn("身分驗證失敗 | msg: " + e.getMessage());
			throw new BusinessException(401, "身分驗證失敗，請檢查帳號密碼");
		}
		log.error("登入程序發生未預期錯誤");
		throw new BusinessException(500, "登入程序發生未預期錯誤");
	}

	@Override
	public void register(UserRegisterDTO user) {
		log.debug("正在註冊用戶 | User: {} Name: {} Email: {}",user, user.getName(),user.getEmail());
		User existingUser = userService.findUserByEmail(user.getEmail());
		if (existingUser != null)
			throw new BusinessException(401, "註冊失敗: 此信箱已被使用");

		User newUser = new User();
		BeanUtils.copyProperties(user, newUser);
		
		String password = generateUserPassword(user);
		user.setPassword(ocPasswordEncoder.encode(password));

		boolean saved = userService.save(newUser);
		if (!saved) {
			log.error("用戶註冊寫入資料庫失敗 | Email: {}", user.getEmail());
			throw new BusinessException(500, "註冊系統繁忙，請稍後再試");
		}
	}

	@Override
	@Transactional
	public void registerBatch(List<UserRegisterDTO> users) {
		log.debug("正在批量註冊用戶");
		if (CollectionUtils.isEmpty(users))
			throw new BusinessException(400, "批量註冊失敗: 資料不符合規範!");

		List<String> emails = users.stream().map(UserRegisterDTO::getEmail).distinct().collect(Collectors.toList());
		List<User> existingUsers = userService.findUsersByEmail(emails);
		Set<String> existingEmailSet = existingUsers.stream().map(User::getEmail).collect(Collectors.toSet());

		List<User> userListWithPwd = new ArrayList<>();
		List<UserRegisterDTO> invalidList = new ArrayList<>();

		for (UserRegisterDTO user : users) {
			if (existingEmailSet.contains(user.getEmail())) {
				invalidList.add(user);
			} else {
				User newUser = new User();
				BeanUtils.copyProperties(user, newUser);
				
				String password = generateUserPassword(user);
				newUser.setPassword(ocPasswordEncoder.encode(password));
			
				
				userListWithPwd.add(newUser);
			}

		}

		if (!invalidList.isEmpty()) {
			log.warn("批量註冊寫入資料庫失敗 | invalidUserList:{}", invalidList);
			throw new BusinessException(400, "批量註冊失敗: 存在不合規的資料", invalidList);
		}
		Boolean saved = userService.saveBatch(userListWithPwd);

		if (!saved) {
			log.error("批量註冊寫入資料庫失敗");
			throw new BusinessException(500, "註冊系統繁忙，請稍後再試");

		}
	}

	@Override
	public void changePassword(UserPasswordChangeDTO dto) {
		log.debug("正在處理用戶更改密碼，User Id: {}", dto.getUserId());
		User user = userService.getById(dto.getUserId());
		if (user == null)
			throw new BusinessException(400, "更改密碼失敗: 用戶id不存在");
		if (!ocPasswordEncoder.matches(dto.getOldPwd(), user.getPassword())) {
			log.warn("更改密碼失敗: 舊密碼錯誤 | User Id: {}", dto.getUserId());
			throw new BusinessException(400, "更改密碼失敗: 舊密碼錯誤");
		}

		var updateWrapper = new LambdaUpdateWrapper<User>().eq(User::getId, user.getId());
		updateWrapper.set(User::getPassword, ocPasswordEncoder.encode(dto.getNewPwd()));

		Boolean updated = userService.update(updateWrapper);
		if (!updated) {
			log.error("更改密碼失敗");
			throw new BusinessException(500, "系統繁忙，請稍後再試");
		}
	}

	private String generateUserPassword(UserRegisterDTO user) {
		String password = user.getPassword();
		if (StringUtils.hasText(password)) {
			return password;
		} else if (StringUtils.hasText(user.getNo())) {
			return user.getNo();
		} else {
			return DEFAULT_PWD;
		}
	}
}
