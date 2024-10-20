package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteRequest {
	private int movieId;
	private int userId;
	
	@Override
    public String toString() {
        return "FavouriteRequest{" +
                "movieId=" + movieId +
                ", userId=" + userId +
                '}';
    }
}
