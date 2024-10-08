package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MovieGenre;
import com.example.demo.repository.MovieGenreRepository;

import java.util.List;

@Service
public class MovieGenreService {
    @Autowired
    private MovieGenreRepository movieGenreRepository;

    public List<MovieGenre> getAllMovieGenres() {
        return movieGenreRepository.findAll();
    }

    public MovieGenre getMovieGenreById(Integer id) {
        return movieGenreRepository.findById(id).orElse(null);
    }

    public MovieGenre createMovieGenre(MovieGenre movieGenre) {
        return movieGenreRepository.save(movieGenre);
    }

    public MovieGenre updateMovieGenre(Integer id, MovieGenre movieGenre) {
        movieGenre.setMovieId(id);
        movieGenre.setIdGenre(id);
        return movieGenreRepository.save(movieGenre);
    }

    public void deleteMovieGenre(Integer id) {
        movieGenreRepository.deleteById(id);
    }
}

