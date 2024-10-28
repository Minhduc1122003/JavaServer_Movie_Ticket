package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.PaymentVNPAYConfig;
import com.example.demo.dto.BuyTicketRequest;
import com.example.demo.mservice.config.Environment;
import com.example.demo.mservice.enums.RequestType;
import com.example.demo.mservice.models.PaymentResponse;
import com.example.demo.mservice.processor.CreateOrderMoMo;
import com.example.demo.mservice.shared.utils.LogUtils;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostMapping("/buy")
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

	@GetMapping("/momo")
	public ResponseEntity<Map<String, String>> createMoMoOrder() {
		LogUtils.init();
		String requestId = String.valueOf(System.currentTimeMillis());
		String orderId = String.valueOf(System.currentTimeMillis());
		long amount = 10000000;
		String orderInfo = "Pay With MoMo";
		String returnURL = "http://localhost:9011/";
		String notifyURL = "https://google.com.vn";

		Environment environment = Environment.selectEnv("dev");
		PaymentResponse captureWalletMoMoResponse = null;
		try {
			captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId,
					Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM, null);
		} catch (Exception e) {
			log.error("Error while processing MoMo payment", e);
		}

		Map<String, String> response = new HashMap<>();
		if (captureWalletMoMoResponse != null && captureWalletMoMoResponse.getPayUrl() != null) {
			log.info("Pay URL: " + captureWalletMoMoResponse.getPayUrl());
			response.put("status", "OK");
			response.put("message", "Successfully");
			response.put("paymentUrl", captureWalletMoMoResponse.getPayUrl());
		} else {
			response.put("status", "FAILED");
			response.put("message", "Failed to create MoMo order");
		}

		return ResponseEntity.ok(response);
	}

	/* Pay with VNPAY */
	@GetMapping("/vnpay")
	public ResponseEntity<Map<String, String>> createVnpayOrder()
			throws UnsupportedEncodingException {
		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";
		String orderType = "other";
		long amount = 100000000 * 100;
		String bankCode = "NCB";

		String vnp_TxnRef = PaymentVNPAYConfig.getRandomNumber(8);
		String vnp_IpAddr = "127.0.0.1";

		String vnp_TmnCode = PaymentVNPAYConfig.vnp_TmnCode;

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");
		vnp_Params.put("vnp_Locale", "vn");
		vnp_Params.put("vnp_BankCode", bankCode);
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
		vnp_Params.put("vnp_OrderType", orderType);
		vnp_Params.put("vnp_ReturnUrl", PaymentVNPAYConfig.vnp_ReturnUrl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		for (String fieldName : fieldNames) {
			String fieldValue = vnp_Params.get(fieldName);
			if (fieldValue != null && fieldValue.length() > 0) {
				hashData.append(fieldName).append('=')
						.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
						.append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = PaymentVNPAYConfig.hmacSHA512(PaymentVNPAYConfig.secretKey, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = PaymentVNPAYConfig.vnp_PayUrl + "?" + queryUrl;

		Map<String, String> response = new HashMap<>();
		response.put("status", "OK");
		response.put("message", "Successfully");
		response.put("paymentUrl", paymentUrl);
		return ResponseEntity.ok(response);
	}

}
