package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.dto.EmailRequest;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.EmailService;
import com.example.demo.service.TokenBlacklistService;
import com.example.demo.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        // Tìm kiếm người dùng trong cơ sở dữ liệu
        User user = customUserDetailsService.findByUsername(authenticationRequest.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tên đăng nhập hoặc mật khẩu.");
        }
        
        // So sánh mật khẩu
        if (!user.getPassword().equals(authenticationRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tên đăng nhập hoặc mật khẩu.");
        }
        
        // Tạo JWT token nếu xác thực thành công
        final String jwt = jwtUtil.generateToken(user);
        UserDTO userDTO = new UserDTO(user.getUserName(), user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getPhoto());
        
        // Trả về JWT token trong response
        return ResponseEntity.ok(new AuthenticationResponse(jwt, userDTO));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
    	String token = request.getHeader("Authorization").substring(7); // Bỏ "Bearer " ra
    	
    	tokenBlacklistService.addToBlacklist(token);
    	return ResponseEntity.ok().build();
    }
    
    @PostMapping("/sendmail")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest){
    	String title = emailRequest.getTitle();
    	String content = emailRequest.getContent();
    	String recipient = emailRequest.getRecipient();
    	
    	try {
			emailService.sendEmail(recipient, title, content);
			return ResponseEntity.ok("Send mail success !!");
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(500).body("Send mail error: " + e.getMessage());
		}
    }

    // Xử lý ngoại lệ toàn cục nếu cần
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
    }
}
