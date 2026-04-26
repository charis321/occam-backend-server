package com.charis.occam_spm_sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.model.dto.UserQueryDTO;
import com.charis.occam_spm_sys.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/user")
@CrossOrigin(value = "http://localhost:5173")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{userId}")
	public Result getUser(@PathVariable Long userId) {
		log.info("正在查詢用戶資訊，ID: {}", userId);
		return Result.success(userService.getUserById(userId));
	}
	
	@GetMapping
	public Result listUsers(UserQueryDTO query) {
		log.info("正在條件查詢用戶資訊，Query: {}", query);
		return Result.success(userService.getUsersByQuery(query));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public Result save(@RequestBody User user){	
		log.info("正在新增用戶，ID: {}", user.getId());
		return userService.save(user)?Result.success("用戶新增成功"):Result.fail("用戶新增失敗");
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{userId}")
	public Result patch(@PathVariable Long userId,
						@RequestBody UserQueryDTO query) {
		log.info("正在更新用戶資訊，Query: {}", query);
		query.setId(userId);
		return userService.patchUserByQuery(query)?Result.success("用戶更新成功"):Result.fail("用戶更新失敗");
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public Result delete(@PathVariable Long userId) {
		log.info("正在刪除用戶，ID: {}", userId);
		return userService.removeById(userId)?Result.success("用戶刪除成功"):Result.fail("用戶刪除失敗");
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
