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
public class ShowtimeDate_Time {
	private Date showtimeDate;
	private Time startTime;
	private int cinemaRoomId;
	private int showtimeId;
}
