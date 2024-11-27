package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MovieGenre;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, Integer> {
	
    @Query(value = """
    		select m.* from MovieGenre m where MovieID = :movieId;
    		""", nativeQuery = true)
    List<MovieGenre> findByMovieId(@Param("movieId") Integer movieId);
    
    @Query(value = """
            SELECT mg.MovieID AS movieId, STRING_AGG(g.GenreName, ',') AS genreNames
			FROM MovieGenre mg
			JOIN Genre g ON mg.IdGenre = g.IdGenre
			WHERE mg.MovieID IN :movieIds
			GROUP BY mg.MovieID;
            """, nativeQuery = true)
        List<Map<String, Object>> findGenreNamesByMovieIds(@Param("movieIds") List<Integer> movieIds);
}
