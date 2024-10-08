package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.MovieGenre;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Integer> {

}
