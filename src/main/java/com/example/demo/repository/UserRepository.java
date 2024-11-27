package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

import jakarta.persistence.Tuple;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUserName(String username);

	User findByUserNameAndPassword(String username, String password);

	List<User> findAllByUserNameNot(String username);

	@Query(value = """
			select photo
			from Users where UserId = :userId;
			""", nativeQuery = true)
	String findPhotoById(@Param("userId") Integer userId);

	@Query(value = """
			select * from Users where Email = :email;
			""", nativeQuery = true)
	User findUserByEmail(@Param("email") String email);

	@Query(value = """
				        select bti.BuyTicketInfoId, m.posterUrl, m.title, st.showtimeDate, st.startTime, STRING_AGG(s.chairCode, ', ') AS chairCodes, c.cinemaName, bti.totalPrice, bti.status
			from Users u
			JOIN BuyTicket bt ON u.UserId = bt.UserId
			JOIN Movies m on bt.MovieID = m.MovieID
			JOIN BuyTicketInfo bti ON bt.BuyTicketId = bti.BuyTicketId
			JOIN TicketSeat ts ON bt.BuyTicketId = ts.BuyTicketId
			JOIN Seats s ON ts.SeatID = s.SeatID
			JOIN Showtime st ON bti.ShowtimeID = st.ShowtimeID
			JOIN CinemaRoom cr ON st.CinemaRoomID = cr.CinemaRoomID
			JOIN Cinemas c ON cr.CinemaID = c.CinemaID
			where u.UserId = 24
			GROUP BY
			bti.BuyTicketInfoId, m.posterUrl, m.title, st.showtimeDate, st.startTime, c.cinemaName, bti.TotalPrice, bti.status;
				""", nativeQuery = true)
	List<Tuple> getTicketByUserId(@Param("userId") Integer userId);
}
