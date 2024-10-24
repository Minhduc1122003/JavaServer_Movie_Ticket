package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MovieDetailDTO;
import com.example.demo.dto.MovieViewDTO;
import com.example.demo.entity.Genre;
import com.example.demo.entity.Movie;
import com.example.demo.entity.Rate;
import com.example.demo.repository.MovieRepository;

import jakarta.persistence.Tuple;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;

	
	public List<Movie> getAllMovies(){
		return movieRepository.findAll();
	}
	
	public List<MovieViewDTO> getAllMoviesView(){
        List<Movie> movies = movieRepository.findAll();
        List<MovieViewDTO> movieDTOs = new ArrayList<>();

        for(Movie movie : movies){
            MovieViewDTO dto = new MovieViewDTO();
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
	
	public List<MovieViewDTO> getMoviesByStatusView(String statusMovie){
        List<Movie> movies = movieRepository.findAll();
        List<MovieViewDTO> movieDTOs = new ArrayList<>();

        for(Movie movie : movies){
        	if(movie != null && statusMovie.equals(movie.getStatusMovie())) {
        		MovieViewDTO dto = new MovieViewDTO();
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
        }

        return movieDTOs;
    }
	
	public MovieDetailDTO getMovieDetails(int movieId, int userId) {
		List<Tuple> tuples = movieRepository.findMovieDetailsByMovieIdAndUserId(movieId, userId);
	    if (tuples.isEmpty()) { // Kiểm tra xem danh sách không rỗng
	    	return null;
	    }
	    
	    Tuple firstTuple = tuples.get(0);
	    
	    MovieDetailDTO dto = new MovieDetailDTO();
	    // Thiết lập các thuộc tính cho dto từ firstTuple
	    dto.setMovieId((Integer) firstTuple.get(0)); // ID phim
	    dto.setTitle((String) firstTuple.get(1)); // Tiêu đề phim
	    dto.setDescription((String) firstTuple.get(2)); // Mô tả phim
	    dto.setDuration((Integer) firstTuple.get(3)); // Thời lượng phim
	    dto.setReleaseDate((Date) firstTuple.get(4)); // Ngày phát hành
	    dto.setPosterUrl((String) firstTuple.get(5)); // URL ảnh poster
	    dto.setTrailerUrl((String) firstTuple.get(6)); // URL video trailer
	    dto.setAge((String) firstTuple.get(7)); // Độ tuổi
	    dto.setSubTitle((String) firstTuple.get(8)); // Phụ đề
	    dto.setVoiceover((String) firstTuple.get(9)); // Lồng ghép
	    dto.setPrice((Float) firstTuple.get(10)); // Giá vé
	    dto.setGenres((String) firstTuple.get(11)); // Thể loại phim
	    dto.setCinemaName((String) firstTuple.get(12)); // Tên rạp
	    dto.setCinemaAddress((String) firstTuple.get(13)); // Địa chỉ rạp
	    dto.setReviewContents((String) firstTuple.get(14)); // Các đánh giá
	    dto.setAverageRating((Float) firstTuple.get(15)); // Đánh giá trung bình
	    dto.setReviewCount((Integer) firstTuple.get(16)); // Số lượng đánh giá
	    dto.setRating9_10((Integer) firstTuple.get(17)); // Số lượng đánh giá từ 9 đến 10
	    dto.setRating7_8((Integer) firstTuple.get(18)); // Số lượng đánh giá từ 7 đến 8
	    dto.setRating5_6((Integer) firstTuple.get(19)); // Số lượng đánh giá từ 5 đến 6
	    dto.setRating3_4((Integer) firstTuple.get(20)); // Số lượng đánh giá từ 3 đến 4
	    dto.setRating1_2((Integer) firstTuple.get(21)); // Số lượng đánh giá từ 1 đến 2
	    dto.setFavourite((Boolean) firstTuple.get(22)); // Kiểm tra nếu là phim yêu thích
	    
	    return dto;
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
			movie.setCinema(movieDetails.getCinema());
            movie.setDescription(movieDetails.getDescription());
            movie.setDuration(movieDetails.getDuration());
            movie.setReleaseDate(movieDetails.getReleaseDate());
            movie.setPosterUrl(movieDetails.getPosterUrl());
            movie.setTrailerUrl(movieDetails.getTrailerUrl());
            movie.setAge(movieDetails.getAge());
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
