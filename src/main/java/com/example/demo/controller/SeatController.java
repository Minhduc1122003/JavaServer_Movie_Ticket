package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@PostMapping("/getShowTime-CinemaRoom")
	public ResponseEntity<?> getChair(@RequestBody SeatRequest seatRequest){
		if(seatRequest == null) {
			return ResponseEntity.badRequest().body("Request body is missing");
		}
		
		try {
			List<SeatResponse> seats = seatService.getSeats(seatRequest.getShowTimeID(), seatRequest.getCinemaRoomID());
			return ResponseEntity.ok(seats);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server: " + e.getMessage());
		}
	}
}
