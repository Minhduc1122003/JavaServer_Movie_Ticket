package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppMtApplication {

	 public static void main(String[] args) {
	        // Lấy cổng từ biến môi trường
	        String port = System.getenv("PORT");
	        if (port == null) {
	            port = "9011"; // Cổng mặc định
	        }

	        // Gán cổng cho Spring Boot
	        SpringApplication.run(AppMtApplication.class, new String[] { "--server.port=" + port });
	    }
}
