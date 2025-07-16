package com.charis.occam_spm_sys.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class OCPasswordEncoder implements PasswordEncoder {
	private final BCryptPasswordEncoder bcryptPasswordEncoder; 
	
	public OCPasswordEncoder() {
		this.bcryptPasswordEncoder = new BCryptPasswordEncoder();
	}
	
	@Override
	public String encode(CharSequence rawPassword) {
		return bcryptPasswordEncoder.encode(rawPassword);
	}
	
	@Override
	public boolean matches(CharSequence rawPassword, String encodePassword) {
		return bcryptPasswordEncoder.matches(rawPassword, encodePassword);
	}
}
