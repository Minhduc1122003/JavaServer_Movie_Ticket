package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.RateByMovie;
import com.example.demo.entity.Rate;
import com.example.demo.service.RateService;
import com.google.api.Http;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rates")
@CrossOrigin(origins = "http://localhost:3000")
public class RateController {
    @Autowired
    private RateService rateService;

    @GetMapping("/getAll")
    public List<Rate> getAllRates() {
        return rateService.getAllRates();
    }
    
    @GetMapping("/getByMovieId/{id}")
    public ResponseEntity<List<RateByMovie>> getAllRateByMovie(@PathVariable int id){
    	List<RateByMovie> rate = rateService.getRateByMovieId(id);
    	return ResponseEntity.ok(rate);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Rate> getRateById(@PathVariable int id) {
        Rate rate = rateService.getRateById(id);
        return rate != null ? ResponseEntity.ok(rate) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRate(@RequestBody Map<String, Object> payload) {
        try {
        	int movieId = (int) payload.get("movieId");
        	int userId = (int) payload.get("userId");
        	String content = (String) payload.get("content");
        	float rating = ((Number) payload.get("rating")).floatValue();
        	
        	Rate rate = new Rate();
        	rate.setContent(content);
        	rate.setRating(rating);
        	
        	rateService.createRate(rate, movieId, userId);

            return ResponseEntity.ok("Review success !");
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error: " + e.getMessage());
		}
    }

    @PutMapping("/update/{id}")
    public Rate updateRate(@PathVariable int id, @RequestBody Rate rate) {
        return rateService.updateRate(id, rate);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable int id) {
        rateService.deleteRate(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/allowRate")
	public ResponseEntity<?> getAllowRevie(@RequestParam int userId, @RequestParam int movieId) {
		boolean allow = rateService.allowRate(userId, movieId);

		if (allow) {
			return ResponseEntity.ok().body(Map.of("status", "success", "message", "Người dùng chưa đánh giá phim này."));
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(Map.of("status", "error", "message", "Người dùng đã đánh giá phim này."));
		}
	}
}

