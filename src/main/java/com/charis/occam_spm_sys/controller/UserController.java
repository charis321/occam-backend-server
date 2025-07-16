package com.charis.occam_spm_sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.charis.occam_spm_sys.common.JWTUtil;
import com.charis.occam_spm_sys.common.JWTUtilOAuth;
import com.charis.occam_spm_sys.common.OCPasswordEncoder;
import com.charis.occam_spm_sys.common.QueryPageParam;
import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.service.StudentService;
import com.charis.occam_spm_sys.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(value = "http://localhost:5173")
public class UserController {
	
	@Autowired
	private UserService userService;
//	@Autowired
//	private StudentService studentService;
	
	@GetMapping("/{userId}")
	public Result getUserInfo(@PathVariable Long userId) {
		return Result.success(userService.getUserInfoVO(userId));
	}
	
	@PostMapping("/save")
	public Result save(@RequestBody User user){	
		return userService.save(user)?Result.success():Result.fail();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list")
	public Result list(){
		return Result.success(userService.getUserInfoVOList());
	}
	
	@PatchMapping("/{userId}")
	public Result update(@PathVariable Long userId,
						 @RequestBody Map<String, Object> params) {
		return userService.patch(userId, params)?Result.success("用戶更新成功"):Result.fail("用戶更新失敗");
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody User user) {
		return userService.removeById(user.getId())?Result.success():Result.fail();
	}
	
//	@PostMapping("listPage")
//	public Result listPage(@RequestBody QueryPageParam query) {
//
//		Page<User> page = new Page<User>();
//		page.setSize(query.getPageSize());
//		page.setCurrent(query.getPageNum());
//		
//		return null;
//	}
}
