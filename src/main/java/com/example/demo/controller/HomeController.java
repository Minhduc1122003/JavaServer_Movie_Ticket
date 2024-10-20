package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<Map<String, Object>> getHomepage() {
        String sql = "SELECT * FROM Users";
        List<Map<String, Object>> users;

        try {
            users = jdbcTemplate.queryForList(sql);
            System.out.println("Clients have connected");
        } catch (Exception e) {
            System.err.println("Error fetching accounts: " + e.getMessage());
            throw new RuntimeException("Internal Server Error", e);
        }

        return users; // Spring Boot sẽ tự động chuyển đổi danh sách này thành JSON
    }
}
