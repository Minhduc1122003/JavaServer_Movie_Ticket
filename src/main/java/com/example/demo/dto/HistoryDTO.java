package com.example.demo.dto;

import java.sql.Time;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {
	private int buyTicketInfoId;
	private String buyTicketId;
	private String posterUrl;
	private String title;
	private Date showtimeDate;
	private Time startTime;
	private String chairCodes;
	private String cinemaName;
	private Double totalPrice;
	private boolean isCheckIn;
}
