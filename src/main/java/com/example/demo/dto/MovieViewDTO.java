package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieViewDTO {
	private String posterUrl;
    private String title;
    private double rating;
    private List<String> genres;
}
