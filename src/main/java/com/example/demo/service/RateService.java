package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Rate;
import com.example.demo.repository.RateRepository;

@Service
public class RateService {
    @Autowired
    private RateRepository rateRepository;

    public List<Rate> getAllRates() {
        return rateRepository.findAll();
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