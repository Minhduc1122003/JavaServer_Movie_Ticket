package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Favourite;
import com.example.demo.entity.Movie;
import com.example.demo.entity.User;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {
    void deleteByMovieAndUser(Movie movie, User user);
}
