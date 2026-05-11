package com.charis.occam_spm_sys.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryConfig {
	
	private String cloudName;
	private String apiKey;
	private String apiSecret;
	
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(Map.of(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret
        ));
    }
}