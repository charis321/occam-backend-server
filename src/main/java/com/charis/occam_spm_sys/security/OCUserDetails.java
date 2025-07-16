package com.charis.occam_spm_sys.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.charis.occam_spm_sys.entity.User;

public class OCUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private final User user;
	private final List<String> roleIndex = new ArrayList<>(Arrays.asList("ROLE_STUDENT",
																		 "ROLE_TEACHER",
																		 "ROLE_ADMIN"));

	public OCUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = roleIndex.get(user.getRole());
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}

	@Override public String getPassword() {
		return "{bcrypt}"+user.getPassword();
	}
	@Override public String getUsername() {return user.getName();}
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

}
