package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.entity.User;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.util.JwtUtil;

@RestController
public class AuthenticationController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        // Tìm kiếm người dùng trong cơ sở dữ liệu
        User user = customUserDetailsService.findByUsername(authenticationRequest.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tên đăng nhập hoặc mật khẩu.");
        }
        
        // So sánh mật khẩu
        if (!user.getPassword().equals(authenticationRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai mật khẩu");
        }

        // Tạo JWT token nếu xác thực thành công
        final String jwt = jwtUtil.generateToken(user);

        // Trả về JWT token trong response
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    // Xử lý ngoại lệ toàn cục nếu cần
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
    }
}
