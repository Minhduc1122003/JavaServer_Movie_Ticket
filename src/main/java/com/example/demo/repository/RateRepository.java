package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Rate;

import jakarta.persistence.Tuple;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer>{
	
	@Query(value = """
			  select u.photo , u.FullName, r.content, r.rating, r.ratingDate
		  from Rate r
		  JOIN Users u ON r.UserId = u.UserId
		  where r.MovieID = :movieId
		  ORDER BY r.RatingDate ASC
			""", nativeQuery = true)
	List<Tuple> getAllRateByMovieId(@Param("movieId") int movieId);
	
	@Query(value = """
			select movieId 
			from rate
			where UserId = :userId and MovieID = :movieId
			""", nativeQuery = true)
	List<Tuple> getRated(@Param("userId") Integer userId, @Param("movieId") Integer movieId);
}
