package com.charis.occam_spm_sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.charis.occam_spm_sys.common.JWTUtil;
import com.charis.occam_spm_sys.common.OCPasswordEncoder;
import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.service.AuthService;
import com.charis.occam_spm_sys.service.UserService;

@Service
public class AuthServiceImpl implements AuthService{
	
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
	public Result login(User user){
		System.out.println(user);
		List<User> userList = userService.findUserByEmail(user.getEmail());
		if(userList.size()==0) return Result.fail("此信箱並未註冊!");
		if(userList.size()!=1) return Result.fail("系統錯誤!");
		if(userList.get(0).getStatus() == 1) return Result.fail("此用戶已停用!");
		
		if(!ocPasswordEncoder.matches(user.getPassword(),userList.get(0).getPassword())){ 
			return Result.fail("密碼錯誤!");
		}
		
		Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
	    );
		
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
	public Result register(User user){
		List<User> userList = userService.findUserByEmail(user.getEmail());
		if(userList.size()>0) return Result.fail("此信箱已註冊!");
		
		String password = ocPasswordEncoder.encode(user.getPassword());
		user.setPassword(password);
		
		return userService.save(user)?Result.success("註冊成功!"):Result.fail();
	}
}
