package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FavouriteRequest;
import com.example.demo.dto.MovieViewDTO;
import com.example.demo.entity.Favourite;
import com.example.demo.service.FavouriteService;



@RestController
@RequestMapping("/favourites")
public class FavouriteController {
	
	@Autowired
	private FavouriteService favouriteService;
	
	@GetMapping("/getAll")
	public List<Favourite> getAllFavourite() {
	    return favouriteService.getAllFavourite();
	}
	
	@PostMapping("/addFavourite")
    public ResponseEntity<String> buyTicket(@RequestBody FavouriteRequest favouriteRequest) {
        
        if(favouriteRequest == null) {
			return ResponseEntity.badRequest().body("Request body is missing");
		}
		System.out.println("Received request: " + favouriteRequest);
		
		// Xử lý request, ví dụ: lấy movieId và userId
        int movieId = favouriteRequest.getMovieId();
        System.out.println(movieId);
        int userId = favouriteRequest.getUserId();
        System.out.println(userId);
        
        try {
			favouriteService.addFavourite(favouriteRequest);
			return ResponseEntity.ok("Favourite insert successfully !");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
		}
    }
	
	
	@DeleteMapping("/deletefavourite")
	public ResponseEntity<?> deleteFavourite(@RequestBody FavouriteRequest favouriteRequest){
		if(favouriteRequest == null) {
			return ResponseEntity.badRequest().body("Request body is missing");
		}
		System.out.println("Received request: " + favouriteRequest);
		
		int movieId = favouriteRequest.getMovieId();
        System.out.println(movieId);
        int userId = favouriteRequest.getUserId();
        System.out.println(userId);
		try {
			favouriteService.deleteFavourite(favouriteRequest);
			return ResponseEntity.ok("Favourite delete successfully !");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
		}
	}
}
