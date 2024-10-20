package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FavouriteRequest;
import com.example.demo.entity.Favourite;
import com.example.demo.service.FavouriteService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/favourites")
public class FavouriteController {
	
	@Autowired
	private FavouriteService favouriteService;
	
	@GetMapping("/getAll")
	public List<Favourite> getAllFavourite() {
	    return favouriteService.getAllFavourite();
	}
	
	@PostMapping("/addfavourite")
	public ResponseEntity<?> addFavourite(@RequestBody FavouriteRequest favouriteRequest) {
		
	    if (favouriteRequest == null) {
	        return ResponseEntity.badRequest().body("Request body is missing");
	    }
	    System.out.println("Received request: " + favouriteRequest);

	    try {
	        favouriteService.addFavourite(favouriteRequest);
	        return ResponseEntity.ok("Favourite added successfully!");
	    } catch (RuntimeException e) {
	    	System.out.println("RuntimeException: " + e.getMessage());
	        return ResponseEntity.badRequest().body(e.getMessage());
	    } catch (Exception e) {
	    	System.out.println("Exception: " + e.getMessage());
	        return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
	    }
	}
	
	@DeleteMapping("/deletefavourite")
	public ResponseEntity<?> deleteFavourite(@RequestBody FavouriteRequest favouriteRequest){
		if(favouriteRequest == null) {
			return ResponseEntity.badRequest().body("Request body is missing");
		}
		
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
