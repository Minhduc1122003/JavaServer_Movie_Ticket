package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowtimeRequest {
	private int movieId;
    private String date;
    private String time;
}
