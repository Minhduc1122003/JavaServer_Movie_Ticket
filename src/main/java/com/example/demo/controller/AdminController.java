package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/api/admin/data")
	public String getAdminData() {
		return "Only admin can access this data.";
	}
}
