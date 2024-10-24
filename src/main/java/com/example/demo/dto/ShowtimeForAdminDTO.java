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
public class ShowtimeForAdminDTO {
    private String movieName;
    private Date showtimeDate;
    private Time startTime;
    private int movieDuration;
    private int roomNumber;
    private String cinemaName;
}
