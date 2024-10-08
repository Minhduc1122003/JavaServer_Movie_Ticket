package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "your-very-long-and-secure-secret-key-that-is-at-least-32-bytes-long";  // Đảm bảo rằng khóa này đủ dài và an toàn cho HS256

    // Phương thức để lấy khóa ký, sử dụng SECRET_KEY
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());  // Sử dụng thuật toán HS256
    }

    // Phương thức tạo token JWT cho người dùng
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("fullName", user.getFullName());
        claims.put("email", user.getEmail()); // Thêm các thông tin cần thiết vào claims (trừ password)

        return Jwts.builder()
                .setClaims(claims)  // Đưa các thông tin vào token
                .setSubject(user.getUserName())  // Đặt subject của token là username
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Thời điểm phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1))  // Token có hiệu lực trong 1 giờ
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // Ký với thuật toán HS256 và khóa bí mật
                .compact();
    }

    // Phương thức lấy username từ token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Phương thức kiểm tra token hết hạn chưa
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Phương thức lấy toàn bộ thông tin từ token (claims)
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())  // Sử dụng cùng khóa ký để giải mã token
                .build()
                .parseClaimsJws(token)
                .getBody();  // Lấy phần body của JWT, chứa các thông tin đã mã hóa
    }
}
