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
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "your-very-long-and-secure-secret-key-that-is-at-least-32-bytes-long";  // Ensure this is at least 256 bits for HS256

    // Generate a key using the Keys class
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", user.getUserName());
        
        // Chuyển đổi vai trò từ int sang chuỗi
        String role = switch (user.getRole()) {
	        case 1 -> "ROLE_STAFF";
	        case 2 -> "ROLE_ADMIN";
	        default -> "ROLE_USER";
	    };
	    claims.put("role", List.of(role));
	    
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(user.getUserId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1)) // 1 tieng
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // Use the updated method
                .compact();
    }
    
    public String extractUsername(String token) {
        return (String) extractAllClaims(token).get("userName");
    }

    public int extractUserId(String token) {
        return Integer.parseInt(extractAllClaims(token).getSubject());
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
    
    public int extractRole(String token) {
        return (int) extractAllClaims(token).get("role"); // Lấy vai trò từ claims
    }

    // Thêm phương thức này để trích xuất tất cả claims từ token
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

