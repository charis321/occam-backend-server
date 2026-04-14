package com.charis.occam_spm_sys.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
		return Result.success(userService.getById(userId));
	}
	
	@GetMapping
	public Result getUsers(UserQueryDTO query) {
		log.info("正在條件查詢用戶資訊，Query: {}", query);
		return Result.success(userService.getUsersByQuery(query));
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public Result save(@RequestBody User user){	
		return userService.save(user)?Result.success():Result.fail();
	}
	
//	@PreAuthorize("hasRole('ADMIN')")
//	@PostMapping("/saveList")
//	public Result save(@RequestBody List<User> user){	
//		return userService.s?Result.success():Result.fail();
//	}
	
//	@PreAuthorize("hasRole('ADMIN')")
//	@GetMapping
//	public Result getUserList(){
//		return Result.success(userService.getUserInfoVOList());
//	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{userId}")
	public Result update(@PathVariable Long userId,
						 @RequestBody Map<String, Object> params) {
		return userService.patch(userId, params)?Result.success("用戶更新成功"):Result.fail("用戶更新失敗");
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
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
