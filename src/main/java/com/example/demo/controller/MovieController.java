package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MovieDetailDTO;
import com.example.demo.dto.MovieRequest;
import com.example.demo.dto.MovieViewDTO;
import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;


@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {
	@Autowired
	private MovieService movieService;
	
	
    @GetMapping("/getAll")
    public List<Movie> getAllMovies(){
    	return movieService.getAllMovies();
    }
    
    @GetMapping("/getAllMovieView")
    public List<MovieViewDTO> getAllMovieView(){
    	return movieService.getAllMoviesView();
    }
    
    @PostMapping("/findByViewMovieID")
    public ResponseEntity<?> findByViewMovieID(@RequestBody MovieRequest movieRequest) {
        if (movieRequest == null || movieRequest.getMovieId() == 0 || movieRequest.getUserId() == 0) {
            return ResponseEntity.badRequest().body("Request body is missing or invalid");
        }

        MovieDetailDTO movieDetail = movieService.getMovieDetails(movieRequest.getMovieId(), movieRequest.getUserId());

        if (movieDetail == null) {
            return ResponseEntity.status(404).body("Movie not found");
        }

        return ResponseEntity.ok(movieDetail);
    }
    
    @GetMapping("/getAllMovieView/{statusMovie}")
    public List<MovieViewDTO> getMovieByStatusView(@PathVariable String statusMovie){
    	return movieService.getMoviesByStatusView(statusMovie);
    }
    
    @GetMapping("/getMovieDetail")
    public ResponseEntity<?> getMovieDetail(@RequestBody int movieId, @RequestBody int userId){
    	MovieDetailDTO dto = movieService.getMovieDetails(movieId, userId);
    	return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/getById/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
    	Movie movie = movieService.getMovieById(id);
    	return movie != null ? ResponseEntity.ok(movie) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/create")
    public Movie createMovie(@RequestBody Movie movie) {
    	return movieService.createMovie(movie);
    }
    
    @PutMapping("/update/{id}")
    public Movie updateMovie(@PathVariable Integer id, @RequestBody Movie movieDetais){
    	return movieService.updateMovie(id, movieDetais);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id){
    	movieService.deleteMovie(id);
    	return ResponseEntity.noContent().build();
    }
    
    
}