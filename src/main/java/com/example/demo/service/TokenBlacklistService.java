package com.example.demo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {
	private Set<String> blacklist = new HashSet<>();
	
	public void addToBlacklist(String token) {
		blacklist.add(token);
	}
	
	public boolean isBlacklisted (String token) {
		return blacklist.contains(token);
	}
	
	public boolean isTokenValid(String token) {
		if(isBlacklisted(token)) {
			return false;
		}
		
		return true;
	}
}
