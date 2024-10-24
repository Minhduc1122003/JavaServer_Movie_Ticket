package com.example.demo.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Showtime;

import jakarta.persistence.Tuple;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {
	@Query(value = """
	        SELECT 
	            s.ShowtimeID AS showtimeId, 
	            m.Title AS title, 
	            m.Duration AS duration, 
	            s.CinemaRoomID AS cinemaRoomId, 
	            s.ShowtimeDate AS showtimeDate, 
	            s.StartTime AS startTime, 
	            DATEADD(MINUTE, m.Duration, CAST(CONVERT(VARCHAR, s.ShowtimeDate, 120) + ' ' + CONVERT(VARCHAR, s.StartTime, 108) AS DATETIME)) AS endTime
	        FROM 
	            Showtime s
	        JOIN 
	            Movies m ON s.MovieID = m.MovieID
	        WHERE 
	            s.ShowtimeDate = :date
	            AND s.StartTime >= :time
	            AND m.MovieID = :movieId
	        ORDER BY 
	            s.StartTime
	        """, nativeQuery = true)
	List<Tuple> findShowtimes(@Param("date") Date date, @Param("time") Time time, @Param("movieId") int movieId);
	
	
	@Query(value = "SELECT " +
            "M.Title AS movieName, " +
            "S.ShowtimeDate AS showtimeDate, " +
            "S.StartTime AS startTime, " +
            "M.Duration AS movieDuration, " +
            "CR.CinemaRoomID AS roomNumber, " +
            "C.CinemaName AS cinemaName " +
            "FROM Showtime S " +
            "INNER JOIN Movies M ON S.MovieID = M.MovieID " +
            "INNER JOIN CinemaRoom CR ON S.CinemaRoomID = CR.CinemaRoomID " +
            "INNER JOIN Cinemas C ON CR.CinemaID = C.CinemaID " +
            "ORDER BY S.ShowtimeDate, S.StartTime", nativeQuery = true)
	List<Tuple> findShowtimeListForAdmin();
	
//	@Query(name = "Showtime.findShowtimeListForAdmin", nativeQuery = true)
//	List<Tuple> findShowtimeListForAdmin();
}
