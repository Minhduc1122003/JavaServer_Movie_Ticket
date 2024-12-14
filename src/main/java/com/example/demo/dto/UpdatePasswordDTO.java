package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDTO {
	private Integer userId;
	private String passwordOld;
	private String passwordNew;
}
