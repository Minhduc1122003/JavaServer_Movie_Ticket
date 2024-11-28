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
		String subject = "Đăng ký tài khoản PANTHERs CINEMA";
		String htmlContent = 
		        "<html>" +
		        "<head>" +
		        "<style>" +
		        "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }" +
		        ".container { width: 90%; max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px;" +
		        "box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); overflow: hidden; }" +
		        ".header { background: #4F75FF; color: #ffffff; padding: 20px; text-align: center; }" +
		        ".header h1 { margin: 0; }" +
		        ".content { padding: 20px; }" +
		        ".footer { background: #f4f4f4; padding: 10px; text-align: center; font-size: 12px; color: #666; }" +
		        ".button { display: inline-block; background: #4F75FF; color: #ffffff; padding: 10px 20px;" +
		        "border-radius: 5px; text-decoration: none; font-weight: bold; }" +
		        "</style>" +
		        "</head>" +
		        "<body>" +
		        "<div class=\"container\">" +
		        "<div class=\"header\">" +
		        "<h1>PANTHERs CINEMA</h1>" +
		        "</div>" +
		        "<div class=\"content\">" +
		        "<p>Chào mừng bạn đến với PANTHERs CINEMA!</p>" +
		        "<p>Để tiếp tục quá trình đăng ký tài khoản, mã code của bạn là:</p>" +
		        "<h2 style=\"text-align: center; color: #4F75FF;\">" + otp + "</h2>" +
		        "<p>Vui lòng sử dụng mã này để hoàn tất quá trình đăng ký.</p>" +
		        "<p>Nếu bạn gặp bất kỳ vấn đề nào, hãy liên hệ với chúng tôi.</p>" +
		        "<p>Chân thành cảm ơn,</p>" +
		        "<p>Đội ngũ PANTHERs CINEMA</p>" +
		        "</div>" +
		        "<div class=\"footer\">" +
		        "<p>Bạn nhận được email này vì bạn đã đăng ký trên PANTHERs CINEMA.</p>" +
		        "<p>Bạn không cần trả lời email này. Nếu bạn cần trợ giúp, vui lòng liên hệ với chúng tôi qua email hỗ trợ.</p>" +
		        "</div>" +
		        "</div>" +
		        "</body>" +
		        "</html>";
		try {
			emailService.sendHtmlEmail(email, subject, htmlContent);
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
