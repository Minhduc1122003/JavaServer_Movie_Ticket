package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;


@Component
public class BearerToken {

	  public static String generateBearerToken() {
	        // Tạo một chuỗi token ngẫu nhiên dài 32 ký tự
	        return RandomStringUtils.randomAlphanumeric(32);
	    }


}

