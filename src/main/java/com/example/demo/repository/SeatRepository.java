package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.SeatResponse;
import com.example.demo.entity.Seat;

import jakarta.persistence.Tuple;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
	@Query(value = """
			SELECT 
            s.SeatID,
            s.CinemaRoomID,
            s.ChairCode,
            s.DefectiveChair,
            COALESCE(sr.Status, 0) AS ReservationStatus
        FROM 
            Seats s
        LEFT JOIN 
            SeatReservation sr ON s.SeatID = sr.SeatID AND sr.ShowtimeID = :showTimeID
        WHERE 
            s.CinemaRoomID = :cinemaRoomID
        ORDER BY 
            s.SeatID
			""", nativeQuery = true)
	List<Tuple> findSeatsByShowtimeAndCinemaRoom(@Param("showTimeID") int showTimeID, @Param("cinemaRoomID") int cinemaRoomID);
}
