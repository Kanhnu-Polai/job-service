package com.skillverify.jobservice.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
	
	@Value("${cloudinary.cloud_name}")
	private String cloudName;
	
	@Value("${cloudinary.api_key}")
	private String apiKey;
	
	
	@Value("${cloudinary.api_secret}")
	private String apiSecret;


    @Bean
    Cloudinary cloudinary() {
		return new Cloudinary(
				com.cloudinary.utils.ObjectUtils.asMap(
						"cloud_name", cloudName,
						"api_key", apiKey,
						"api_secret", apiSecret
				)
		);
	}
}
