package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping(value = "/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

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
    public ResponseEntity<?> getPaymentVnpay(@RequestParam long amount, @RequestParam String id) {
        try {
            Map<String, String> response = paymentService.paymentWithVnPay(amount, id);
            log.info("Pay with Vnpay susscessfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error while processing Vnpay payment : {}", e);
            return ResponseEntity.badRequest().body("Error while processing Vnpay payment");
        }

    }

}
