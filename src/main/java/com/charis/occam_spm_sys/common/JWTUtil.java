package com.charis.occam_spm_sys.common;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.charis.occam_spm_sys.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTUtil {
   private static final String SECRET_KEY = "betelgeusesiriuscanopusandromeda";
   private static final long EXPIRATION_TIME = 1000 * 60 * 60 *24;
//   private static final long EXPIRATION_TIME = 1000;
   private final Key key =  Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

   public String generateToken(User user) {
	   
	   log.debug("開始為用戶生成Token, Name:{}, ID: {}", user.getName(), user.getId());
	   
	   Map<String, Object> claims = new HashMap<>();
	   claims.put("id", user.getId());
	   claims.put("name", user.getName());
	   claims.put("email", user.getEmail());
	   claims.put("role", user.getRole());
	   String token = Jwts.builder()
			   			.setSubject(user.getName())
			   			.setClaims(claims)
			   			.setIssuedAt(new Date())
			   			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) 
			   			.signWith(key, SignatureAlgorithm.HS256)
			   			.compact();
	   log.info("Token生成成功: {}", user.getName());
	   return token;
   }
   
   public Map<String, Object> parseToken(String token) {
	   Claims claims = 	Jwts.parserBuilder()
			   				.setSigningKey(key)
			   				.build()
			   				.parseClaimsJws(token)
			   				.getBody();
	   
	   Map<String, Object> payload = new HashMap<>(claims);
	   return payload;
   }
//   	
//   public String extractEmail(String token) {
//	   return (String)parseToken(token).get("email");
//   }
   public boolean isTokenExpired(String token) {
	   try {
		   Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); 
	   }catch(Exception e){
		   return false;
	   }
	   
	   return true;
   }
}
