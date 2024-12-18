package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.MovieDetailDTO;
import com.example.demo.entity.Movie;

import jakarta.persistence.Tuple;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	@Query("SELECT m FROM Movie m WHERE m.title = :title")
    List<Movie> findByTitle(@Param("title") String title);
	
	
	@Query(value = """
			SELECT 
			    m.movieID, 
				m.statusMovie,
			    m.title, 
			    m.description, 
			    m.duration, 
			    m.releaseDate, 
			    m.posterUrl, 
			    m.trailerUrl, 
			    m.age, 
			    m.subTitle, 
			    m.voiceover, 
			    m.price, 
				(SELECT STRING_AGG (a.Name, ', ')
				 FROM MovieActors ma
				 JOIN Actors a ON ma.ActorID = a.ActorID
				 WHERE ma.MovieID = m.MovieID) AS actors,
			    (SELECT STRING_AGG(g.genreName, ', ') 
			     FROM MovieGenre mg 
			     JOIN Genre g ON mg.idGenre = g.idGenre 
			     WHERE mg.movieID = m.movieID) AS genres, 
			    c.cinemaName, 
			    c.address AS cinemaAddress, 
			    STRING_AGG(r.content, ' | ') AS reviewContents, 
			    ROUND(AVG(r.rating), 2) AS averageRating, 
			    COUNT(r.idRate) AS reviewCount, 
			    SUM(CASE WHEN r.rating BETWEEN 9 AND 10 THEN 1 ELSE 0 END) AS rating9_10, 
			    SUM(CASE WHEN r.rating BETWEEN 7 AND 8 THEN 1 ELSE 0 END) AS rating7_8, 
			    SUM(CASE WHEN r.rating BETWEEN 5 AND 6 THEN 1 ELSE 0 END) AS rating5_6, 
			    SUM(CASE WHEN r.rating BETWEEN 3 AND 4 THEN 1 ELSE 0 END) AS rating3_4, 
			    SUM(CASE WHEN r.rating BETWEEN 1 AND 2 THEN 1 ELSE 0 END) AS rating1_2, 
			    CASE WHEN f.movieID IS NOT NULL THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END AS isFavourite 
			FROM 
			    Movies m 
			LEFT JOIN 
			    Cinemas c ON m.cinemaID = c.cinemaID 
			LEFT JOIN 
			    Rate r ON m.movieID = r.movieID 
			LEFT JOIN 
			    Favourite f ON m.movieID = f.movieID AND f.userId = :userId
			
			WHERE 
			    m.movieID = :movieId
			GROUP BY 
			    m.movieID, 
				m.statusMovie,
			    m.title, 
			    m.description, 
			    m.duration, 
			    m.releaseDate, 
			    m.posterUrl, 
			    m.trailerUrl, 
			    m.age, 
			    m.subTitle, 
			    m.voiceover, 
			    m.price, 
			    c.cinemaName, 
			    c.address, 
			    f.movieID;
			""",
            nativeQuery = true)
    List<Tuple> findMovieDetailsByMovieIdAndUserId(@Param("movieId") int movieId, @Param("userId") int userId);
	
	@Query(value = """
	        SELECT 
	            m.movieID,
				m.statusMovie,
	            m.title, 
	            m.description, 
	            m.duration, 
	            m.releaseDate, 
	            m.posterUrl, 
	            m.trailerUrl, 
	            m.age, 
	            m.subTitle, 
	            m.voiceover, 
	            m.price, 
	            (SELECT STRING_AGG(a.Name, ', ')
	             FROM MovieActors ma
	             JOIN Actors a ON ma.ActorID = a.ActorID
	             WHERE ma.MovieID = m.MovieID) AS actors,
	            (SELECT STRING_AGG(g.genreName, ', ') 
	             FROM MovieGenre mg 
	             JOIN Genre g ON mg.idGenre = g.idGenre 
	             WHERE mg.movieID = m.movieID) AS genres, 
	            c.cinemaName, 
	            c.address AS cinemaAddress, 
	            STRING_AGG(r.content, ' | ') AS reviewContents, 
	            ROUND(AVG(r.rating), 2) AS averageRating, 
	            COUNT(r.idRate) AS reviewCount, 
	            SUM(CASE WHEN r.rating BETWEEN 9 AND 10 THEN 1 ELSE 0 END) AS rating9_10, 
	            SUM(CASE WHEN r.rating BETWEEN 7 AND 8 THEN 1 ELSE 0 END) AS rating7_8, 
	            SUM(CASE WHEN r.rating BETWEEN 5 AND 6 THEN 1 ELSE 0 END) AS rating5_6, 
	            SUM(CASE WHEN r.rating BETWEEN 3 AND 4 THEN 1 ELSE 0 END) AS rating3_4, 
	            SUM(CASE WHEN r.rating BETWEEN 1 AND 2 THEN 1 ELSE 0 END) AS rating1_2, 
	            CAST(0 AS BIT) AS isFavourite
	        FROM 
	            Movies m 
	        LEFT JOIN 
	            Cinemas c ON m.cinemaID = c.cinemaID 
	        LEFT JOIN 
	            Rate r ON m.movieID = r.movieID 
	        WHERE 
	            m.movieID = :movieId
	        GROUP BY 
	            m.movieID, 
				m.statusMovie,
	            m.title, 
	            m.description, 
	            m.duration, 
	            m.releaseDate, 
	            m.posterUrl, 
	            m.trailerUrl, 
	            m.age, 
	            m.subTitle, 
	            m.voiceover, 
	            m.price, 
	            c.cinemaName, 
	            c.address;
	        """, nativeQuery = true)
	List<Tuple> findMovieDetailsByMovieId(@Param("movieId") int movieId);
	
	@Query(value = """
			SELECT 
			    m.MovieID,
			    m.PosterUrl,
				m.Title,
			    (SELECT STRING_AGG(g.GenreName, ', ') 
			        FROM MovieGenre mg
			        JOIN Genre g ON mg.IdGenre = g.IdGenre
			        WHERE mg.MovieID = m.MovieID
			    ) AS Genres
			FROM 
			    Movies m
			LEFT JOIN  
			    Favourite f ON m.MovieID = f.MovieID
			where f.UserId = :userId
			GROUP BY 
			    m.MovieID, m.Title, m.PosterUrl
			""", nativeQuery = true)
	List<Tuple> findMovieByUserId(@Param("userId") int userId);
	
	@Query(value = """
			  SELECT 
			    m.MovieID,
			    m.PosterUrl,
				m.Title,
			    (SELECT STRING_AGG(g.GenreName, ', ') 
			        FROM MovieGenre mg
			        JOIN Genre g ON mg.IdGenre = g.IdGenre
			        WHERE mg.MovieID = m.MovieID
			    ) AS Genres, 
			    ROUND(AVG(r.Rating), 2) AS AverageRating
			FROM 
			    Movies m
			LEFT JOIN  
			    Rate r ON m.MovieID = r.MovieID
			
			GROUP BY 
			    m.MovieID, m.Title, m.PosterUrl
			""", nativeQuery = true)
	List<Tuple> findAllMovieView();
	
	@Query(value = """
			  SELECT 
			    m.MovieID,
			    m.PosterUrl,
				m.Title,
			    (SELECT STRING_AGG(g.GenreName, ', ') 
			        FROM MovieGenre mg
			        JOIN Genre g ON mg.IdGenre = g.IdGenre
			        WHERE mg.MovieID = m.MovieID
			    ) AS Genres, 
			    ROUND(AVG(r.Rating), 2) AS AverageRating
			FROM 
			    Movies m
			LEFT JOIN  
			    Rate r ON m.MovieID = r.MovieID
			Where
				m.StatusMovie = :status
			GROUP BY 
			    m.MovieID, m.Title, m.PosterUrl
			""", nativeQuery = true)
	List<Tuple> findMovieByStatus(@Param("status") String status);
	
	@Query(value = """
			  SELECT 
			    m.MovieID,
			    m.PosterUrl,
				m.Title,
			    (SELECT STRING_AGG(g.GenreName, ', ') 
			        FROM MovieGenre mg
			        JOIN Genre g ON mg.IdGenre = g.IdGenre
			        WHERE mg.MovieID = m.MovieID
			    ) AS Genres, 
			    ROUND(AVG(r.Rating), 2) AS AverageRating
			FROM 
			    Movies m
			LEFT JOIN  
			    Rate r ON m.MovieID = r.MovieID
			JOIN
				MovieGenre mg ON mg.MovieID = m.MovieID
			JOIN
				Genre g ON g.IdGenre = mg.IdGenre
			where g.GenreName = :genre

			GROUP BY 
			    m.MovieID, m.Title, m.PosterUrl
			""", nativeQuery = true)
	List<Tuple> findMovieByGenre(@Param("genre") String genre);
}
