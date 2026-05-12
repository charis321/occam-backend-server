package com.charis.occam_spm_sys.config;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.charis.occam_spm_sys.filter.JWTAuthFilter;

@Configuration
@EnableWebSecurity()
@EnableMethodSecurity
public class SecurityConfig{
	
	@Autowired
	private JWTAuthFilter jwtAuthFilter;
	
	@Value("${app.cors.allowed-origins}")
    private String allowedOrigins;
	
//	@Autowired
//	private OCAuthenticationProvider ocAuthenticationProvider;
	
	@Bean
	public SecurityFilterChain securityFilterchain(HttpSecurity http) throws Exception {
		http
//			.cors(Customizer.withDefaults())
			.cors(c->c.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable())
//			.authenticationProvider(ocAuthenticationProvider)
			.authorizeHttpRequests(auth->auth
				.requestMatchers("/api/auth/**").permitAll()	
				.requestMatchers("/ws/**").permitAll()
				.requestMatchers("/api/**").authenticated()
				)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			;			
		return http.build();
	}
	
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(allowedOrigins.split(",")));
        configuration.setAllowCredentials(true);
        
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
