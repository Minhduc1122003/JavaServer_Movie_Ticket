package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailRequest {
	private String title;
	private String content;
	private String recipient;
}
