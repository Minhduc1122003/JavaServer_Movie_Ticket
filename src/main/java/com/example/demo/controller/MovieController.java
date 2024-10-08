package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MovieDTO;
import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;


@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {
	@Autowired
	private MovieService movieService;
	
	
    @GetMapping
    public List<Movie> getAllMovies(){
    	return movieService.getAllMovies();
    }
    
    @GetMapping("/DTO")
    public List<MovieDTO> getAllMovieDTO(){
    	return movieService.getAllMoviesDTO();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
    	Movie movie = movieService.getMovieById(id);
    	return movie != null ? ResponseEntity.ok(movie) : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
    	return movieService.createMovie(movie);
    }
    
    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable Integer id, @RequestBody Movie movieDetais){
    	return movieService.updateMovie(id, movieDetais);
    }
    
    @DeleteMapping
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id){
    	movieService.deleteMovie(id);
    	return ResponseEntity.noContent().build();
    }
    
    
}