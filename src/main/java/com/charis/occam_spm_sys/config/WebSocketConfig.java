package com.charis.occam_spm_sys.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.charis.occam_spm_sys.common.JWTUtil;
import com.charis.occam_spm_sys.security.OCUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private OCUserDetailsService ocUserDetailsService;
	
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 開啟一個簡單的記憶體訊息代理
        // /topic 用於廣播，/queue 用於點對點
        config.enableSimpleBroker("/topic", "/queue");
        // 前端發送訊息時的前綴
        config.setApplicationDestinationPrefixes("/instant");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 註冊 WebSocket 連線端點，允許跨域
        registry.addEndpoint("/ws")
        		.setAllowedOriginPatterns("http://localhost:5173")	
                .withSockJS(); // 支援 SockJS 回退機制
    }
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
        	
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String authToken = accessor.getFirstNativeHeader("Authorization");
                    if (authToken != null && authToken.startsWith("Bearer ")) {
                        String token = authToken.substring(7);
                        // 1. 驗證 JWT 效期與簽名
                        // 2. 解析出用戶資訊 (如 userId, role)
                        // 3. 將用戶資訊存入 SecurityContext
                        // accessor.setUser(userPrincipal); 
                		try {
                			Map<String, Object> claims = jwtUtil.parseToken(token);
                			String email = (String) claims.get("email");
                			log.debug("用戶WS-JWT驗證成功 | Email:{} ", email);

                			UserDetails userDetails = ocUserDetailsService.loadUserByUsername(email);
                			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                					userDetails, null, userDetails.getAuthorities());
                			
                			accessor.setUser(authenticationToken); 
                			log.debug("WS授權成功: {}", email);
                			
                		} catch (ExpiredJwtException e) {
                			log.warn("JWT憑證已過期 | Msg: {}", e.getMessage());
                			throw new MessageDeliveryException("JWT憑證已過期");
                		} catch (JwtException | IllegalArgumentException e) {
                			log.warn("JWT解析異常 | Msg:{}", e.getMessage());
                			throw new MessageDeliveryException("JWT解析異常");
                		} catch (Exception e) {
                			log.error("WS授權異常", e);
                			throw new MessageDeliveryException("WS授權異常");
                		}
                		
                    } else {
                        throw new MessageDeliveryException("未經授權的連線");
                    }
                }
                return message;
            }
        });
    }
}