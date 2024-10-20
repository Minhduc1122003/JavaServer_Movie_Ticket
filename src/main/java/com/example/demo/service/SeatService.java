package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SeatResponse;
import com.example.demo.entity.Seat;
import com.example.demo.repository.SeatRepository;

import jakarta.persistence.Tuple;

@Service
public class SeatService {
	
	@Autowired
	private SeatRepository seatRepository;
	
	public List<Seat> getAll(){
		return seatRepository.findAll();
	}
	
	public List<SeatResponse> getSeats(int showTimeID, int cinemaRoomID) {
		List<Tuple> tuples = seatRepository.findSeatsByShowtimeAndCinemaRoom(showTimeID, cinemaRoomID);
		if (tuples.isEmpty()) { // Kiểm tra xem danh sách không rỗng
	    	return null;
	    }
		
		return tuples.stream().map(tuple -> {
			SeatResponse dto = new SeatResponse();
			dto.setSeatID(tuple.get("seatID", Integer.class));
			dto.setCinemaRoomID(tuple.get("cinemaRoomID", Integer.class));
			dto.setChairCode(tuple.get("chairCode", String.class));
			dto.setDefectiveChair(tuple.get("defectiveChair", Boolean.class));
			dto.setReservationStatus(tuple.get("reservationStatus", Boolean.class));
			
			return dto;
		}).collect(Collectors.toList());
	}
}
