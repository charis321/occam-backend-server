package com.charis.occam_spm_sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.User;
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
		log.info("用戶登入嘗試! Email: {} Name: {}",user.getEmail(),user.getName());
		return authService.login(user);
	}
	
	@PostMapping("/register")
	public Result register(@RequestBody User user) {
		log.info("新用戶註冊請求! Email: {} Name: {}",user.getEmail(),user.getName());
		return authService.register(user);
	}
}