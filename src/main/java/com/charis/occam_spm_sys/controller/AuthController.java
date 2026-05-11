package com.charis.occam_spm_sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.model.dto.UserAuthDTO;
import com.charis.occam_spm_sys.model.dto.UserLoginDTO;
import com.charis.occam_spm_sys.model.dto.UserPasswordChangeDTO;
import com.charis.occam_spm_sys.model.dto.UserRegisterDTO;
import com.charis.occam_spm_sys.service.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public Result login(@RequestBody @Valid UserLoginDTO userLogin) {
		log.debug("用戶登入嘗試! Email: {} Name: {}", userLogin.getEmail());
		UserAuthDTO userAuth = authService.login(userLogin);
		return Result.success("登入成功!", userAuth);
	}

	@PostMapping("/register")
	public Result register( @RequestBody @Valid UserRegisterDTO user) {
		log.debug("新用戶註冊請求! Email: {} Name: {}", user.getEmail(), user.getName());
		authService.register(user);
		return Result.success("註冊成功!");
	}

	@PostMapping("/registerBatch")
	public Result register(@RequestBody @Valid List<UserRegisterDTO> userList) {
		log.debug("新用戶批量註冊請求");
		authService.registerBatch(userList);
		return Result.success("批量註冊成功!");
	}
	
	@PutMapping("/password")
	public Result changePassword(@RequestBody @Valid UserPasswordChangeDTO dto) {
		log.debug("用戶更改密碼請求");
		authService.changePassword(dto);
		return Result.success("更改密碼成功!");
	}

}