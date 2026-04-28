package com.charis.occam_spm_sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.model.dto.UserAuthDTO;
import com.charis.occam_spm_sys.model.dto.UserPasswordChangeDTO;
import com.charis.occam_spm_sys.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public Result login(@RequestBody User user) {
		log.debug("用戶登入嘗試! Email: {} Name: {}", user.getEmail(), user.getName());
		UserAuthDTO dto = authService.login(user);
		return Result.success("登入成功!", dto);
	}

	@PostMapping("/register")
	public Result register(@RequestBody User user) {
		log.debug("新用戶註冊請求! Email: {} Name: {}", user.getEmail(), user.getName());
		authService.register(user);
		return Result.success("註冊成功!");
	}

	@PostMapping("/registerBatch")
	public Result register(@RequestBody List<User> userList) {
		log.debug("新用戶批量註冊請求");
		authService.registerBatch(userList);
		return Result.success("批量註冊成功!");
	}
	
	@PutMapping("/password")
	public Result changePassword(@RequestBody UserPasswordChangeDTO dto) {
		log.info("用戶更改密碼請求");
		authService.changePassword(dto);
		return Result.success("更改密碼成功!");
	}

}