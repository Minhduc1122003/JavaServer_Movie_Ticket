package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Genre;
import com.example.demo.repository.GenreRepository;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(int idGenre) {
        return genreRepository.findById(idGenre).orElse(null);
    }

    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre updateGenre(int idGenre, Genre genre) {
        genre.setIdGenre(idGenre);
        return genreRepository.save(genre);
    }

    public void deleteGenre(int idGenre) {
        genreRepository.deleteById(idGenre);
    }
}

