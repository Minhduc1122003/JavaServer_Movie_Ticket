package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**")
	                    .allowedOrigins("http://localhost:3000", "http://192.168.1.117:3000")  // Thay đổi nếu React chạy ở cổng khác
	                    .allowedMethods("GET", "POST", "PUT", "DELETE")
	                    .allowedHeaders("*")
	                    .allowCredentials(true);
	        }
	    };
	}
}

