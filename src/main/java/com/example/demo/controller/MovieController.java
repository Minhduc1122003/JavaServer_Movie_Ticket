package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.MovieDetailDTO;
import com.example.demo.dto.MovieRequest;
import com.example.demo.dto.MovieViewDTO;
import com.example.demo.dto.MovieViewFavourite;
import com.example.demo.entity.Movie;
import com.example.demo.repository.MovieRepository;
import com.example.demo.service.FirebaseStorageService;
import com.example.demo.service.MovieService;


@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
    private FirebaseStorageService firebaseStorageService;
	
    @GetMapping("/getAll")
    public List<Movie> getAllMovies(){
    	return movieRepository.findAll();
    }
    
    @GetMapping("/getAllMovieView")
    public List<MovieViewDTO> getAllMovieView(){
    	return movieService.getAllMoviesView();
    }
    
    @GetMapping("/getAllFavouriteMovieByUserId/{id}")
	public List<MovieViewFavourite> getAllByUserId(@PathVariable int id){
		return movieService.getAllMoviesViewByUserId(id);
	}
    
    @GetMapping("/getAllMovieViewStatus/{statusMovie}")
    public List<MovieViewDTO> getMovieByStatusView(@PathVariable String statusMovie){
    	return movieService.getMoviesByStatusView(statusMovie);
    }
    
    @GetMapping("/getAllMovieViewGenre/{genre}")
    public List<MovieViewDTO> getMovieByGenreView(@PathVariable String genre){
    	return movieService.getMoviesByGenreView(genre);
    }
    
    @GetMapping("/getMovieDetail/{movieId}")
    public ResponseEntity<MovieDetailDTO> getMovieDetail(@PathVariable int movieId, @RequestParam(required = false) Integer userId) {
        MovieDetailDTO dto = movieService.getMovieDetails(movieId, userId);
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/getById/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
    	Movie movie = movieService.getMovieById(id);
    	return movie != null ? ResponseEntity.ok(movie) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file){
    	try {
    		String imgUrl = firebaseStorageService.uploadFile(file);

    		return ResponseEntity.status(HttpStatus.OK).body(imgUrl);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi server");
		}
    }
    
    
    @PostMapping("/create")
    public ResponseEntity<Movie> createMovie(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("duration") Integer duration,
            @RequestParam("releaseDate") String releaseDate,
            @RequestParam("posterUrl") MultipartFile posterFile, // File ảnh
            @RequestParam("trailerUrl") String trailerUrl,
            @RequestParam("age") String age,
            @RequestParam("subTitle") Boolean subTitle,
            @RequestParam("voiceover") Boolean voiceover,
            @RequestParam("statusMovie") String statusMovie,
            @RequestParam("price") double price) {

        try {
            // Upload hình ảnh poster lên Firebase và lấy URL
            String posterUrl = firebaseStorageService.uploadFile(posterFile);

            // Tạo đối tượng Movie với các thông tin đã nhận
            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setDescription(description);
            movie.setDuration(duration);
            movie.setReleaseDate(Date.valueOf(releaseDate)); // Chuyển đổi từ String sang Date
            movie.setPosterUrl(posterUrl); // Đặt URL của ảnh đã upload vào posterUrl
            movie.setTrailerUrl(trailerUrl);
            movie.setAge(age);
            movie.setSubTitle(subTitle);
            movie.setVoiceover(voiceover);
            movie.setStatusMovie(statusMovie);
            movie.setPrice(price);
            movie.setIsDelete(false); // Thiết lập mặc định là false

            // Lưu movie vào cơ sở dữ liệu
            Movie saveMovie = movieService.createMovie(movie);
            return ResponseEntity.ok(saveMovie);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // Xử lý lỗi nếu upload hoặc lưu thất bại
        }
    }
    
    // Xử lý lưu ảnh vào trong server khi insert
    private String savePosterFile(MultipartFile posterFile) {
        // Đường dẫn đến thư mục lưu trữ ảnh
        String uploadDir = "uploads"; // Bạn có thể thay đổi đường dẫn này nếu cần
        String fileName = posterFile.getOriginalFilename();
        String filePath = uploadDir + File.separator + fileName;

        // Tạo thư mục nếu nó không tồn tại
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        try {
            // Lưu file vào thư mục
            Path path = Paths.get(filePath);
            Files.copy(posterFile.getInputStream(), path);
        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý lỗi nếu cần
        }

        // Trả về đường dẫn URL để truy cập file
        return "http://localhost:9011/" + filePath; // Điều chỉnh nếu cần
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