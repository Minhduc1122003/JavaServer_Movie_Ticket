package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Rate;
import com.example.demo.service.RateService;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
@CrossOrigin(origins = "http://localhost:3000")
public class RateController {
    @Autowired
    private RateService rateService;

    @GetMapping
    public List<Rate> getAllRates() {
        return rateService.getAllRates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rate> getRateById(@PathVariable int id) {
        Rate rate = rateService.getRateById(id);
        return rate != null ? ResponseEntity.ok(rate) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Rate createRate(@RequestBody Rate rate) {
        return rateService.createRate(rate);
    }

    @PutMapping("/{id}")
    public Rate updateRate(@PathVariable int id, @RequestBody Rate rate) {
        return rateService.updateRate(id, rate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable int id) {
        rateService.deleteRate(id);
        return ResponseEntity.noContent().build();
    }
}

