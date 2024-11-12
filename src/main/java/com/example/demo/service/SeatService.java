package com.example.demo.service;

import java.util.ArrayList;
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
	
	public List<SeatResponse> findSeatsByShowtimeAndCinemaRoom(int showtimeId, int cinemaRoomId) {
	    List<Tuple> results = seatRepository.findSeatsByShowtimeAndCinemaRoom(showtimeId, cinemaRoomId);
	    List<SeatResponse> seatDtos = new ArrayList<>();

	    for (Tuple tuple : results) {
	        SeatResponse seatDto = new SeatResponse(
	                (int) tuple.get(0), // seatID
	                (int) tuple.get(1), // cinemaRoomID
	                (String) tuple.get(2), // chairCode
	                (boolean) tuple.get(3), // defectiveChair
	                ((int) tuple.get(4)) == 1 // reservationStatus, chuyển đổi từ int sang boolean
	        );
	        seatDtos.add(seatDto);
	    }

	    return seatDtos;
	}

}
