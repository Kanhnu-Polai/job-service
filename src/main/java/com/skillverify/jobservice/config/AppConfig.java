package com.skillverify.jobservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {
	
	// Provide configuration for RestClient
	
	@Bean
	RestClient restClient() {
		return RestClient.create();
	}

}
