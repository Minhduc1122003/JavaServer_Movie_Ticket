package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.MovieDTO;
import com.example.demo.entity.Genre;
import com.example.demo.entity.Movie;
import com.example.demo.entity.Rate;
import com.example.demo.repository.MovieRepository;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
    private RestTemplate restTemplate;
	
	public List<Movie> getAllMovies(){
		return movieRepository.findAll();
	}
	
	public List<MovieDTO> getAllMoviesDTO(){
        List<Movie> movies = movieRepository.findAll();
        List<MovieDTO> movieDTOs = new ArrayList<>();

        for(Movie movie : movies){
            MovieDTO dto = new MovieDTO();
            dto.setPosterUrl(movie.getPosterUrl());
            dto.setTitle(movie.getTitle());

            // Lấy rating trung bình
            List<Rate> rates = movie.getRates();
            double averageRating = 0;
            if(rates != null && !rates.isEmpty()){
                averageRating = rates.stream().mapToDouble(Rate::getRating).average().orElse(0);
                averageRating = Math.round(averageRating * 10.0) / 10.0;
            }
            dto.setRating(averageRating);

            // Lấy tên thể loại
            List<Genre> genres = movie.getGenres();
            List<String> genreNames = new ArrayList<>();
            if(genres != null){
                for(Genre genre : genres){
                    genreNames.add(genre.getGenreName());
                }
            }
            dto.setGenres(genreNames);

            movieDTOs.add(dto);
        }

        return movieDTOs;
    }
	
	public Movie getMovieById(Integer id) {
		return movieRepository.findById(id).orElse(null);
	}
	
	public Movie createMovie(Movie movie) {
		return movieRepository.save(movie);
	}
	
	public Movie updateMovie(Integer id, Movie movieDetails) {
		Movie movie = movieRepository.findById(id).orElse(null);
		if(movie != null) {
			movie.setTitle(movieDetails.getTitle());
			movie.setCinemaID(movieDetails.getCinemaID());
            movie.setDescription(movieDetails.getDescription());
            movie.setDuration(movieDetails.getDuration());
            movie.setReleaseDate(movieDetails.getReleaseDate());
            movie.setPosterUrl(movieDetails.getPosterUrl());
            movie.setTrailerUrl(movieDetails.getTrailerUrl());
            movie.setSubTitle(movieDetails.getSubTitle());
            movie.setVoiceover(movieDetails.getVoiceover());
            movie.setStatusMovie(movieDetails.getStatusMovie());
            movie.setIsDelete(movieDetails.getIsDelete());
            return movieRepository.save(movie);
		}
		return null;
	}
	
	public void deleteMovie(Integer id) {
		movieRepository.deleteById(id);
	}
}
