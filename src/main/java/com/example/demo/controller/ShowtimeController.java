package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ShowtimeDTO;
import com.example.demo.dto.ShowtimeForAdminDTO;
import com.example.demo.dto.ShowtimeRequest;
import com.example.demo.entity.Movie;
import com.example.demo.entity.Showtime;
import com.example.demo.service.ShowtimeService;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/showtime")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    @GetMapping("/getAll")
    public List<Showtime> getAll(){
    	return showtimeService.getAll();
    }
    
    @GetMapping("/getListByAdmin")
    public List<ShowtimeForAdminDTO> getListByAdmin(){
    	return showtimeService.getShowtimeListForAdmin();
    }
    
    @PostMapping("/getById-date-time")
    public ResponseEntity<List<ShowtimeDTO>> getShowtime(@RequestBody ShowtimeRequest request) {
    	// Chuyển đổi từ chuỗi (String) sang Date và Time
        LocalDate localDate = LocalDate.parse(request.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime localTime = LocalTime.parse(request.getTime(), DateTimeFormatter.ofPattern("HH:mm"));
        
        // Chuyển LocalDate và LocalTime sang kiểu java.sql.Date và java.sql.Time
        Date sqlDate = Date.valueOf(localDate);
        Time sqlTime = Time.valueOf(localTime);
        
        List<ShowtimeDTO> showtimes = showtimeService.getShowtimes(request.getMovieId(), sqlDate, sqlTime);
        return ResponseEntity.ok(showtimes);
    }
}
