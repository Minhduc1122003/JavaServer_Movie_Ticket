package com.example.demo.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ShowtimeDTO;
import com.example.demo.dto.ShowtimeForAdminDTO;
import com.example.demo.entity.Showtime;
import com.example.demo.repository.ShowtimeRepository;

import jakarta.persistence.Tuple;

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;
    
    public List<Showtime> getAll(){
    	return showtimeRepository.findAll();
    }

    public List<ShowtimeDTO> getShowtimes(int movieId, Date date, Time time) {
    	List<Tuple> tuples = showtimeRepository.findShowtimes(date, time, movieId);
    	if (tuples.isEmpty()) { // Kiểm tra xem danh sách không rỗng
	    	return null;
	    }
    	
        return tuples.stream().map(tuple -> {
        	ShowtimeDTO dto = new ShowtimeDTO();
        	dto.setShowtimeID(tuple.get("showtimeID", Integer .class));
        	dto.setMovieTitle(tuple.get("movieTitle", String.class));
        	dto.setMovieDuration(tuple.get("movieDuration", Integer.class));
        	dto.setCinemaRoomID(tuple.get("cinemaRoomID", Integer.class));
        	dto.setShowtimeDate(tuple.get("showtimeDate", Date.class));
        	dto.setStartTime(tuple.get("startTime", Time.class));
        	dto.setEndTime(tuple.get("endTime", Time.class));
        	return dto;
        }).collect(Collectors.toList());
    }
    
    public List<ShowtimeForAdminDTO> getShowtimeListForAdmin(){
    	List<Tuple> tuples = showtimeRepository.findShowtimeListForAdmin();
    	if (tuples.isEmpty()) { // Kiểm tra xem danh sách không rỗng
	    	return null;
	    }
        
        return tuples.stream().map(tuple -> {
            ShowtimeForAdminDTO dto = new ShowtimeForAdminDTO();
            dto.setMovieName(tuple.get("movieName", String.class));
            dto.setShowtimeDate(tuple.get("showtimeDate", Date.class));
            dto.setStartTime(tuple.get("startTime", Time.class));
            dto.setMovieDuration(tuple.get("movieDuration", Integer.class));
            dto.setRoomNumber(tuple.get("roomNumber", Integer.class));
            dto.setCinemaName(tuple.get("cinemaName", String.class));
            return dto;
        }).collect(Collectors.toList());
    }
}
