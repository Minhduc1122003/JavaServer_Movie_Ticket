package com.example.demo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailDTO {
	private int movieId;
    private String title;
    private String description;
    private int duration;
    private Date releaseDate;
    private String posterUrl;
    private String trailerUrl;
    private String age;
    private String subTitle;
    private String voiceover;
    private float price;
    private String genres;
    private String cinemaName;
    private String cinemaAddress;
    private String reviewContents; // Các đánh giá kết hợp thành chuỗi
    private float averageRating; // Đánh giá trung bình
    private int reviewCount; // Số lượng đánh giá
    private int rating9_10; // Số lượng đánh giá từ 9 đến 10
    private int rating7_8; // Số lượng đánh giá từ 7 đến 8
    private int rating5_6; // Số lượng đánh giá từ 5 đến 6
    private int rating3_4; // Số lượng đánh giá từ 3 đến 4
    private int rating1_2; // Số lượng đánh giá từ 1 đến 2
    private boolean isFavourite; // Kiểm tra nếu là phim yêu thích
}
