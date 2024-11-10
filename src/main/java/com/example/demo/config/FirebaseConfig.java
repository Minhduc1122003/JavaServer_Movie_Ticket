package com.example.demo.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {
	
	@Bean
	public FirebaseApp firebaseApp() throws IOException {
		// Sử dụng getResourceAsStream để hỗ trợ cả môi trường phát triển và sản phẩm
		InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase-service-account.json");
		
		if (serviceAccount == null) {
			throw new IOException("Không tìm thấy tệp JSON xác thực Firebase");
		}

		FirebaseOptions options = FirebaseOptions.builder()
												.setCredentials(GoogleCredentials.fromStream(serviceAccount))
												.setStorageBucket("movieticket-77cf5.appspot.com")
												.build();
		
		if(FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}
		
		return FirebaseApp.getInstance();
	}
}
