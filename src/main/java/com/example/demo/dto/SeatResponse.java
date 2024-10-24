package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {
	private int seatID;
	private int cinemaRoomID;
    private String chairCode;
    private boolean defectiveChair;
    private boolean reservationStatus;
}
