package com.charis.occam_spm_sys.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.charis.occam_spm_sys.common.JWTUtil;
import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.security.OCUserDetails;
import com.charis.occam_spm_sys.security.OCUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter{
	
	private final JWTUtil jwtUtil;
	private final OCUserDetailsService ocUserDetailsService;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public JWTAuthFilter(JWTUtil jwtUtil, OCUserDetailsService ocUserDetailsService) {
		this.jwtUtil = jwtUtil;
		this.ocUserDetailsService = ocUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String auth = request.getHeader("Authorization");
		if(auth != null && auth.startsWith("Bearer ")){
			try {
				String token = auth.substring(7);	
				String email = (String)jwtUtil.parseToken(token).get("email");
				System.out.println("filter email: "+ email);

				UserDetails userDetails = ocUserDetailsService.loadUserByUsername(email);
				UsernamePasswordAuthenticationToken authenticationToken =
	                  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticationToken); 
				filterChain.doFilter(request, response);
			}catch(ExpiredJwtException e) {
				sendResponse(response, 401, "JWT_EXPIRED");
			}
		}else {
			filterChain.doFilter(request, response);
		}
		
	}
	
	private void sendResponse(HttpServletResponse response, int code, String msg) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(code);
        
        String body = objectMapper.writeValueAsString(Result.fail(code,msg));
        response.getWriter().write(body);
    }

}
