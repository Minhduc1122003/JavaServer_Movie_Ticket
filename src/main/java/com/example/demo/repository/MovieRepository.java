package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Movie;

import jakarta.persistence.Tuple;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	@Query("SELECT m FROM Movie m WHERE m.title = :title")
    List<Movie> findByTitle(@Param("title") String title);
	
	
	@Query(value = "SELECT m.movieID, m.title, m.description, m.duration, m.releaseDate, " +
            "m.posterUrl, m.trailerUrl, m.age, m.subTitle, m.voiceover, m.price, " +
            "(SELECT STRING_AGG(g.genreName, ', ') " +
            " FROM MovieGenre mg " +
            " JOIN Genre g ON mg.idGenre = g.idGenre " +
            " WHERE mg.movieID = m.movieID) AS genres, " +
            "c.cinemaName, c.address AS cinemaAddress, " +
            "STRING_AGG(r.content, ' | ') AS reviewContents, " +
            "ROUND(AVG(r.rating), 2) AS averageRating, " +
            "COUNT(r.idRate) AS reviewCount, " +
            "SUM(CASE WHEN r.rating BETWEEN 9 AND 10 THEN 1 ELSE 0 END) AS rating9_10, " +
            "SUM(CASE WHEN r.rating BETWEEN 7 AND 8 THEN 1 ELSE 0 END) AS rating7_8, " +
            "SUM(CASE WHEN r.rating BETWEEN 5 AND 6 THEN 1 ELSE 0 END) AS rating5_6, " +
            "SUM(CASE WHEN r.rating BETWEEN 3 AND 4 THEN 1 ELSE 0 END) AS rating3_4, " +
            "SUM(CASE WHEN r.rating BETWEEN 1 AND 2 THEN 1 ELSE 0 END) AS rating1_2, " +
            "CASE WHEN f.movieID IS NOT NULL THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END AS isFavourite " +
            "FROM Movies m " +
            "LEFT JOIN Cinemas c ON m.cinemaID = c.cinemaID " +
            "LEFT JOIN Rate r ON m.movieID = r.movieID " +
            "LEFT JOIN Favourite f ON m.movieID = f.movieID AND f.userId = :userId " +
            "WHERE m.movieID = :movieId " +
            "GROUP BY m.movieID, m.title, m.description, m.duration, m.releaseDate, " +
            "m.posterUrl, m.trailerUrl, m.age, m.subTitle, m.voiceover, " +
            "m.price, c.cinemaName, c.address, f.movieID",
            nativeQuery = true)
    List<Tuple> findMovieDetailsByMovieIdAndUserId(@Param("movieId") int movieId, @Param("userId") int userId);
}
