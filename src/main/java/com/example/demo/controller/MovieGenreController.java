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

import com.example.demo.entity.MovieGenre;
import com.example.demo.service.MovieGenreService;

@RestController
@RequestMapping("/api/movieGenres")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieGenreController {
    @Autowired
    private MovieGenreService movieGenreService;

    @GetMapping("/getAll")
    public List<MovieGenre> getAllMovieGenres() {
        return movieGenreService.getAllMovieGenres();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<MovieGenre> getMovieGenreById(@PathVariable int id) {
        MovieGenre movieGenre = movieGenreService.getMovieGenreById(id);
        return movieGenre != null ? ResponseEntity.ok(movieGenre) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public MovieGenre createMovieGenre(@RequestBody MovieGenre movieGenre) {
        return movieGenreService.createMovieGenre(movieGenre);
    }

    @PutMapping("/update/{id}")
    public MovieGenre updateMovieGenre(@PathVariable int id, @RequestBody MovieGenre movieGenre) {
        return movieGenreService.updateMovieGenre(id, movieGenre);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMovieGenre(@PathVariable int id) {
        movieGenreService.deleteMovieGenre(id);
        return ResponseEntity.noContent().build();
    }
}
