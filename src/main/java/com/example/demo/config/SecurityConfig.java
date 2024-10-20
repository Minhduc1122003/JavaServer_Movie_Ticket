package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.demo.filter.JwtRequestFilter;
import com.example.demo.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
    private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
    private CustomUserDetailsService customUserDetailsService;

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(true);
                config.addAllowedOriginPattern("*"); // React app URL
                config.addAllowedHeader("*");
                config.addAllowedMethod("*");
                return config;
            }))
            .authorizeHttpRequests(authorize -> authorize
            		.requestMatchers("/api/admin/**").hasRole("ADMIN") // Chỉ admin mới được truy cập
                    .requestMatchers("/api/staff/**").hasAnyRole("STAFF", "ADMIN") // Staff và admin được truy cập
                    .requestMatchers("/api/user/**").hasAnyRole("USER", "STAFF", "ADMIN") // User, staff và admin được truy cập
            		.anyRequest().permitAll()
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Đăng ký bộ lọc

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder()); // Cấu hình dịch vụ người dùng
        return authenticationManagerBuilder.build();
    }
}
