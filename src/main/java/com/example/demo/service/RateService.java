package com.example.demo.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RateByMovie;
import com.example.demo.entity.Rate;
import com.example.demo.repository.RateRepository;
import jakarta.persistence.Tuple;

@Service
public class RateService {
    @Autowired
    private RateRepository rateRepository;

    public List<Rate> getAllRates() {
        return rateRepository.findAll();
    }
    
    public List<RateByMovie> getRateByMovieId(int movieId){
    	List<Tuple> tuples = rateRepository.getAllRateByMovieId(movieId);
    	
    	if (tuples.isEmpty()) {
    	    return Collections.emptyList();
    	}
    	
    	return tuples.stream().map(tuple -> {
    		RateByMovie dto = new RateByMovie();
    		
    		dto.setFullName(tuple.get("fullName", String.class));
    		dto.setContent(tuple.get("content", String.class));
    		dto.setRating(tuple.get("rating", Double.class));
    		Timestamp timestamp = tuple.get("ratingDate", Timestamp.class);
    		if (timestamp != null) {
    		    dto.setRatingDate(timestamp.toLocalDateTime());
    		}
    		return dto;
    	}).collect(Collectors.toList());
    }

    public Rate getRateById(int idRate) {
        return rateRepository.findById(idRate).orElse(null);
    }

    public Rate createRate(Rate rate) {
        return rateRepository.save(rate);
    }

    public Rate updateRate(int idRate, Rate rate) {
        rate.setIdRate(idRate);
        return rateRepository.save(rate);
    }

    public void deleteRate(int idRate) {
        rateRepository.deleteById(idRate);
    }
}