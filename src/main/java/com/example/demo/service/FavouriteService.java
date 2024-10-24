package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.FavouriteRequest;
import com.example.demo.entity.Favourite;
import com.example.demo.entity.Movie;
import com.example.demo.entity.User;
import com.example.demo.repository.FavouriteRepository;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class FavouriteService {
	
	@Autowired
	private FavouriteRepository favouriteRepository;
	
	@Autowired
    private MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<Favourite> getAllFavourite() {
	    return favouriteRepository.findAll();
	}
	
	@Transactional
	public void addFavourite(FavouriteRequest favouriteRequest) {
		System.out.println("Inside addFavourite method");
		Movie movie = movieRepository.findById(favouriteRequest.getMovieId()).orElse(null);
	    if (movie == null) {
	        throw new RuntimeException("Movie not found");
	    }
	    
	    User user = userRepository.findById(favouriteRequest.getUserId()).orElse(null);
	    if (user == null) {
	        throw new RuntimeException("User not found");
	    }
	    
	    System.out.println("Movie: " + movie);
	    System.out.println("User: " + user);

	    Favourite favourite = new Favourite();
	    favourite.setMovie(movie);
	    favourite.setUser(user);

	    favouriteRepository.save(favourite);
	}
	
	@Transactional
	public void deleteFavourite(FavouriteRequest favouriteRequest) {
		Movie movie = movieRepository.findById(favouriteRequest.getMovieId()).orElse(null);
		User user = userRepository.findById(favouriteRequest.getUserId()).orElse(null);
		
		if(movie == null && user == null) {
			throw new RuntimeException();
		}

		favouriteRepository.deleteByMovieAndUser(movie, user);
	}
}
