package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.PaymentVNPAYConfig;
import com.example.demo.repository.BuyTicketInfoRepository;
import com.example.demo.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping(value = "/api/payments")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;

	@Autowired
	public BuyTicketInfoRepository buyTicketInfoRepository;

	@GetMapping("/momo")
	public ResponseEntity<?> getPaymentMomo(@RequestParam long amount, @RequestParam String id) {
		try {
			Map<String, String> response = paymentService.paymentWithMomo(amount, id);
			log.info("Pay with Momo susscessfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Error while processing Momo payment : {}", e);
			return ResponseEntity.badRequest().body("Error while processing Momo payment");
		}

	}

@GetMapping("/vnpay")
public ResponseEntity<?> getPaymentVnpay(@RequestParam long amount, @RequestParam String id, HttpServletRequest request) {
    log.info("Received VNPay payment request for id: {}, amount: {}", id, amount);
    try {
        Map<String, String> response = paymentService.paymentWithVnPay(amount, id, request);
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        log.error("Error while processing VNPay payment for id: {}", id, e);
        return ResponseEntity.badRequest().body("Error while processing VNPay payment");
    }
}
@GetMapping("/server-time")
public ResponseEntity<?> getServerTime() {
    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String serverTime = formatter.format(cld.getTime());
    return ResponseEntity.ok(serverTime);
}



	@PostMapping("/vnpay/callback")
	public ResponseEntity<?> handleVnpayCallback(@RequestBody Map<String, String> params) {
	    log.info("Received callback from VNPay: {}", params);
	    try {
	        // Lấy các tham số cần thiết từ callback
	        String vnp_ResponseCode = params.get("vnp_ResponseCode");
	        String id = params.get("id");
	        // Kiểm tra các tham số cần thiết
	        if (vnp_ResponseCode == null) {
	            log.error("Missing required parameters in callback");
	            return ResponseEntity.badRequest().body("Missing required parameters");
	        }


	        // Kiểm tra mã phản hồi từ VNPay (00 là thành công)
	        if ("00".equals(vnp_ResponseCode)) {
	            // Thực hiện hành động khi thanh toán thành công
	        	Integer numId = Integer.parseInt(id);
	            buyTicketInfoRepository.updateStatusById(numId, "Đã thanh toán");
	            log.info("Payment success");
	            return ResponseEntity.ok("Payment success!");
	        } else {
	            log.error("Payment failed with Response Code: {}", vnp_ResponseCode);
	            return ResponseEntity.badRequest().body("Payment failed");
	        }
	    } catch (Exception e) {
	        log.error("Error processing callback: {}", e.getMessage(), e);
	        return ResponseEntity.badRequest().body("Error processing callback");
	    }
	}

}
