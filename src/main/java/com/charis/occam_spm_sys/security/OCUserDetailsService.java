package com.charis.occam_spm_sys.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.service.UserService;

@Service
public class OCUserDetailsService implements UserDetailsService {

	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//use email for auth
//		System.out.println("email:"+email);
		User user = userService.findUserByEmail(email);
		if(user==null) {throw new UsernameNotFoundException("email 有誤");}
		
		UserDetails userDetails = new OCUserDetails(user);
//		System.out.println(userDetails.getAuthorities());
		return userDetails;
	}
}
