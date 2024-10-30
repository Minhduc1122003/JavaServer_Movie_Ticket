package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BuyTicketRequest;
import com.example.demo.service.PaymentService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private PaymentService paymentService;

	@PostMapping("/createBuyTicket")
	public ResponseEntity<?> insertBuyTicket(@RequestBody BuyTicketRequest buyTicketRequest) {
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Internal server error: " + e.getMessage());
		}
	}

	/* Pay with MOMO */
	@GetMapping("/momo")
	public ResponseEntity<Map<String, String>> createMoMoOrder(@RequestParam long amount, @RequestParam String id) {
		try {
			Map<String, String> response = paymentService.paymentWithMomo(amount, id);
			log.info("Payment with MoMo Successful!!!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Payment with Momo fail : {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	/* Pay with VNPAY */
	@GetMapping("/vnpay")
	public ResponseEntity<Map<String, String>> createVnpayOrder(@RequestParam long amount, @RequestParam String id)
			throws UnsupportedEncodingException {
		try {
			long vnpayAmount = amount * 100;
			Map<String, String> response = paymentService.paymentWithVnPay(vnpayAmount, id);
			log.info("Payment with VNPay Successful!!!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Payment with VNpay fail : {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
