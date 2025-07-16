package com.charis.occam_spm_sys.common;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTUtilOAuth {
	
	private static final String KEY = "betelgeuse";
	
	public static String getToken(Map<String, Object> payload) {	
		return 	JWT.create().withClaim("payload", payload)
							.withExpiresAt(new Date(System.currentTimeMillis()*100000000))
							.sign(Algorithm.HMAC256(KEY));
	}
	
	public static Map<String, Object> parseToken(String token){
		return JWT.require(Algorithm.HMAC256(KEY)).build()
					.verify(token)
					.getClaim("payload")
					.asMap();
	}	
}
