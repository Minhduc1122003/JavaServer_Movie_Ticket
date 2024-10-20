package com.example.demo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.util.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        int userId = 0;
        String jwt = null;

        // Kiểm tra xem token có trong header Authorization hay không
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Lấy token
            userId = jwtUtil.extractUserId(jwt); // Giải mã userId từ token
        }

        // Nếu userId không có trong SecurityContext thì xác thực
        if (userId != 0 && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Tải UserDetails bằng userId
            UserDetails userDetails = this.customUserDetailsService.loadUserById(userId);
            
            if (!jwtUtil.isTokenExpired(jwt)) {
                // Trích xuất claims từ token
                Map<String, Object> claims = jwtUtil.extractAllClaims(jwt);
                
                List<String> roles = (List<String>) claims.get("role");
                
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                
                // Tạo Authentication với vai trò (authorities)
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                
                // Thiết lập chi tiết yêu cầu
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Đặt Authentication vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        chain.doFilter(request, response); // Tiếp tục với chuỗi bộ lọc
    }
}
