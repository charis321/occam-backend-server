package com.charis.occam_spm_sys.filter;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.charis.occam_spm_sys.common.JWTUtil;
import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.security.OCUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

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
		String path = request.getRequestURI();

		if (auth == null || !auth.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = auth.substring(7);
		try {
			Map<String, Object> claims = jwtUtil.parseToken(token);
			String email = (String) claims.get("email");
			log.debug("用戶JWT驗證成功 | Email:{} | Resquest Path:{}", email, path);

			UserDetails userDetails = ocUserDetailsService.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			log.warn("JWT 已過期 | Resquest Path: {} | Msg: {}", path, e.getMessage());
			sendResultResponse(response, 401, "登入憑證已過期，請重新登入");
		} catch (JwtException | IllegalArgumentException e) {
			log.warn("JWT 解析異常 | Msg:{}", e.getMessage());
			sendResultResponse(response, 401, "無效的登入憑證");
		} catch (Exception e) {
			log.error("系統安全過濾異常", e);
			sendResultResponse(response, 500, "安全系統發生問題");
		}

	}

	private void sendResultResponse(HttpServletResponse response, int code, String msg) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(code);

		String body = objectMapper.writeValueAsString(Result.fail(code, msg));
		response.getWriter().write(body);
	}

}
