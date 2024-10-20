package com.example.demo.dto;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeDTO {
	private int showtimeID;
    private String movieTitle;
    private int movieDuration;
    private int cinemaRoomID;
    private Date showtimeDate;
    private Time startTime;
    private Time endTime;
}
