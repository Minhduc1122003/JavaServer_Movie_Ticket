package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SeatRequest;
import com.example.demo.dto.SeatResponse;
import com.example.demo.entity.Seat;
import com.example.demo.service.SeatService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
	
	@Autowired
	private SeatService seatService;
	
	@GetMapping("/getAll")
	public List<Seat> getAll(){
		return seatService.getAll();
	}
	
	@GetMapping("/showtime/{showtimeId}/cinemaRoom/{cinemaRoomId}")
    public ResponseEntity<List<SeatResponse>> getSeatsByShowtimeAndCinemaRoom(
            @PathVariable int showtimeId,
            @PathVariable int cinemaRoomId) {
        List<SeatResponse> seats = seatService.findSeatsByShowtimeAndCinemaRoom(showtimeId, cinemaRoomId);
        return ResponseEntity.ok(seats);
    }
}
