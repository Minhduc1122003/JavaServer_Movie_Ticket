package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BuyTicketRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@PostMapping("/createBuyTicket")
	public ResponseEntity<?> insertBuyTicket(@RequestBody BuyTicketRequest buyTicketRequest){
		try {
			// Lấy dữ liệu từ RequestBody
			int buyTicketId = buyTicketRequest.getBuyTicketId();
			int userId = buyTicketRequest.getUserId();
            int movieId = buyTicketRequest.getMovieId();
            int quantity = buyTicketRequest.getQuantity();
            float totalPrice = buyTicketRequest.getTotalPrice();
            int showtimeId = buyTicketRequest.getShowtimeId();
            List<Integer> seatIDs = buyTicketRequest.getSeatIDs();
            String seatIDsString = String.join(",", seatIDs.stream()
                    .map(String::valueOf)
                    .collect(Collectors.toList()));
            
            // Gọi stored
            jdbcTemplate.update("EXEC InsertBuyTicket ?, ?, ?, ?, ?, ?, ?",
            		buyTicketId, userId, movieId, quantity, totalPrice, showtimeId, seatIDsString);
            
            return ResponseEntity.ok("Insert success !");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
		}
	}
}
