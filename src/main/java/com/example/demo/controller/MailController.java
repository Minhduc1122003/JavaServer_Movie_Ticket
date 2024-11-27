package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OtpRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;

@RestController
@RequestMapping("/api/mail")
@CrossOrigin(origins = "http://localhost:3000")
public class MailController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;

	private final ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();

	private String generateOTP() {
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000); // Tạo mã OTP 6 chữ số
		return String.valueOf(otp);
	}

	@PostMapping("/sendMail")
	public ResponseEntity<?> sendEmail(@RequestBody String email) {
		// Kiểm tra xem email có tồn tại trong bảng user không
		email = email.replace("\"", "").trim();
		User emailExists = userRepository.findUserByEmail(email);

		if (emailExists == null) {
			return ResponseEntity.status(404).body("Email not found in our records.");
		}

		String otp = generateOTP();
		String title = "Mã OTP của bạn";
		String content = "Mã OTP của bạn là: " + otp;

		try {
			emailService.sendEmail(email, title, content);
			otpStorage.put(email, otp);
			Map<String, Object> response = new HashMap<>();
			response.put("message", "Send mail success !!");
			response.put("userId", emailExists.getUserId()); // Lấy userId từ user

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(500).body("Send mail error: " + e.getMessage());
		}
	}

	@PostMapping("/verifyOTP")
	public ResponseEntity<?> verifyOTP(@RequestBody OtpRequest otpRequest) {
		String email = otpRequest.getEmail();
		String otp = otpRequest.getOtp();

		String storeOtp = otpStorage.get(email);

		if (storeOtp != null && storeOtp.equals(otp)) {
			otpStorage.remove(email); // Xóa mã OTP sau khi xác minh thành công
			return ResponseEntity.ok("OTP verified successfully, proceed to the next page.");

		} else {
			return ResponseEntity.status(400).body("Invalid OTP.");
		}
	}
}
