package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MovieDetailDTO;
import com.example.demo.dto.MovieViewDTO;
import com.example.demo.entity.Genre;
import com.example.demo.entity.Movie;
import com.example.demo.entity.MovieGenre;
import com.example.demo.entity.Rate;
import com.example.demo.repository.GenreRepository;
import com.example.demo.repository.MovieGenreRepository;
import com.example.demo.repository.MovieRepository;

import jakarta.persistence.Tuple;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MovieGenreRepository movieGenreRepository;

	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	public List<MovieViewDTO> getAllMoviesView() {
		List<Tuple> tuples = movieRepository.findAllMovieView();

		if (tuples == null) {
			return null;
		}

		return tuples.stream().map(tuple -> {
			MovieViewDTO dto = new MovieViewDTO();

			dto.setMovieId(tuple.get("MovieID", Integer.class));
			dto.setPosterUrl(tuple.get("PosterUrl", String.class));
			dto.setTitle(tuple.get("Title", String.class));
			String genresString = tuple.get("Genres", String.class);
			List<String> genres = Arrays.asList(genresString.split(",\\s*"));
			dto.setGenres(genres);
			if (tuple.get("AverageRating") == null) {
				dto.setRating(0);
			} else {
				dto.setRating(tuple.get("AverageRating", Double.class));
			}
			return dto;
		}).collect(Collectors.toList());
	}

	public List<MovieViewDTO> getAllMoviesViewByUserId(int userId) {
		List<Movie> movies = movieRepository.findMovieByUserId(userId);
		List<Integer> movieIds = movies.stream().map(Movie::getMovieId).collect(Collectors.toList());

		// Truy vấn thể loại cho tất cả bộ phim trong một lần
		Map<Integer, List<String>> genreNamesMap = new HashMap<>();
		List<Map<String, Object>> genreResults = movieGenreRepository.findGenreNamesByMovieIds(movieIds);

		for (Map<String, Object> result : genreResults) {
			Integer movieId = (Integer) result.get("movieId");
			String genreNamesStr = (String) result.get("genreNames");
			List<String> genreNames = Arrays.asList(genreNamesStr.split(","));
			genreNamesMap.put(movieId, genreNames);
		}

		List<MovieViewDTO> movieDTOs = new ArrayList<>();
		for (Movie movie : movies) {
			MovieViewDTO dto = new MovieViewDTO();
			dto.setMovieId(movie.getMovieId());
			dto.setPosterUrl(movie.getPosterUrl());
			dto.setTitle(movie.getTitle());

			// Tính điểm trung bình
			List<Rate> rates = movie.getRates();
			double averageRating = rates != null && !rates.isEmpty()
					? rates.stream().mapToDouble(Rate::getRating).average().orElse(0)
					: 0;
			dto.setRating(Math.round(averageRating * 10.0) / 10.0);

			// Lấy thể loại từ Map đã truy vấn trước
			List<String> genreNames = genreNamesMap.getOrDefault(movie.getMovieId(), Collections.emptyList());
			dto.setGenres(genreNames);

			movieDTOs.add(dto);
		}

		return movieDTOs;
	}

	public List<MovieViewDTO> getMoviesByStatusView(String statusMovie) {
		List<Tuple> tuples = movieRepository.findMovieByStatus(statusMovie);

		if (tuples == null) {
			return null;
		}

		return tuples.stream().map(tuple -> {
			MovieViewDTO dto = new MovieViewDTO();

			dto.setMovieId(tuple.get("MovieID", Integer.class));
			dto.setPosterUrl(tuple.get("PosterUrl", String.class));
			dto.setTitle(tuple.get("Title", String.class));
			String genresString = tuple.get("Genres", String.class);
			List<String> genres = Arrays.asList(genresString, ", ");
			dto.setGenres(genres);
			if (tuple.get("AverageRating") == null) {
				dto.setRating(0);
			} else {
				dto.setRating(tuple.get("AverageRating", Double.class));
			}
			return dto;
		}).collect(Collectors.toList());
	}

	public MovieDetailDTO getMovieDetails(int movieId, Integer userId) {
		List<Tuple> tuples;

		// Kiểm tra nếu userId là null
		if (userId == null) {
			// Gọi repository mà không cần userId
			tuples = movieRepository.findMovieDetailsByMovieId(movieId);
		} else {
			// Gọi repository với userId
			tuples = movieRepository.findMovieDetailsByMovieIdAndUserId(movieId, userId);
		}

		// Kiểm tra xem danh sách không rỗng
		if (tuples.isEmpty()) {
			return null; // Có thể ném ngoại lệ thay vì trả về null
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
		dto.setSubTitle((Boolean) firstTuple.get(8)); // Phụ đề
		dto.setVoiceover((Boolean) firstTuple.get(9)); // Lồng ghép

		dto.setPrice((Double) firstTuple.get(10)); // Giá vé
		dto.setActors((String) firstTuple.get(11)); // Diễn viên
		dto.setGenres((String) firstTuple.get(12)); // Thể loại phim
		dto.setCinemaName((String) firstTuple.get(13)); // Tên rạp
		dto.setCinemaAddress((String) firstTuple.get(14)); // Địa chỉ rạp
		dto.setReviewContents((String) firstTuple.get(15)); // Các đánh giá
		if (firstTuple.get(16) == null) {
			dto.setAverageRating(0);
			dto.setReviewCount(0); // Số lượng đánh giá
			dto.setRating9_10(0); // Số lượng đánh giá từ 9 đến 10
			dto.setRating7_8(0); // Số lượng đánh giá từ 7 đến 8
			dto.setRating5_6(0); // Số lượng đánh giá từ 5 đến 6
			dto.setRating3_4(0); // Số lượng đánh giá từ 3 đến 4
			dto.setRating1_2(0); // Số lượng đánh giá từ 1 đến 2
		} else {
			dto.setAverageRating((Double) firstTuple.get(16)); // Đánh giá trung bình
			dto.setReviewCount((Integer) firstTuple.get(17)); // Số lượng đánh giá
			dto.setRating9_10((Integer) firstTuple.get(18)); // Số lượng đánh giá từ 9 đến 10
			dto.setRating7_8((Integer) firstTuple.get(19)); // Số lượng đánh giá từ 7 đến 8
			dto.setRating5_6((Integer) firstTuple.get(20)); // Số lượng đánh giá từ 5 đến 6
			dto.setRating3_4((Integer) firstTuple.get(21)); // Số lượng đánh giá từ 3 đến 4
			dto.setRating1_2((Integer) firstTuple.get(22)); // Số lượng đánh giá từ 1 đến 2
		}
		Object value = firstTuple.get(23);
		if (value instanceof Boolean) {
			dto.setFavourite((Boolean) value);
		} else {
			// Xử lý khi giá trị không phải là boolean
			dto.setFavourite(false); // hoặc true tùy theo logic của bạn
		}

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
		if (movie != null) {
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
