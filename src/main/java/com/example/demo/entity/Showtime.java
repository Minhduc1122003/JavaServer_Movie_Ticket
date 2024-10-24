package com.example.demo.entity;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Showtime")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@NamedNativeQuery(
//		name = "Showtime.findShowtimeListForAdmin",
//		query = """
//			SELECT 
//		    M.Title AS MovieName,
//		    S.ShowtimeDate,
//		    S.StartTime,
//		    M.Duration AS MovieDuration,
//		    CR.CinemaRoomID AS RoomNumber,
//		    C.CinemaName
//		FROM 
//		    Showtime S
//		    INNER JOIN Movies M ON S.MovieID = M.MovieID
//		    INNER JOIN CinemaRoom CR ON S.CinemaRoomID = CR.CinemaRoomID
//		    INNER JOIN Cinemas C ON CR.CinemaID = C.CinemaID
//		ORDER BY 
//		    S.ShowtimeDate, S.StartTime
//		""",
//		resultSetMapping = "ShowtimeForAdminDTOMapping"
//)
//@SqlResultSetMapping(
//	    name = "ShowtimeForAdminMapping",
//	    classes = @ConstructorResult(
//	        targetClass = ShowtimeForAdminDTO.class,
//	        columns = {
//	            @ColumnResult(name = "movieName"),
//	            @ColumnResult(name = "showtimeDate", type = Date.class),
//	            @ColumnResult(name = "startTime", type = Time.class),
//	            @ColumnResult(name = "movieDuration"),
//	            @ColumnResult(name = "roomNumber"),
//	            @ColumnResult(name = "cinemaName")
//	        }
//	    )
//	)

public class Showtime {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ShowtimeID")
	private int showtimeId;
	
	private Date showtimeDate;
	private LocalTime startTime;
	
	@OneToMany(mappedBy = "showtime")
	@JsonManagedReference
	private List<BuyTicketInfo> buyTicketInfo;
	
	@OneToMany(mappedBy = "showtime")
	@JsonManagedReference
	private List<SeatReservation> seatReservation;
	
	@ManyToOne
	@JoinColumn(name = "MovieID", referencedColumnName = "MovieID", nullable = false)
	@JsonBackReference
	private Movie movie;
	
	@ManyToOne
	@JoinColumn(name = "CinemaRoomID", referencedColumnName = "CinemaRoomID", nullable = false)
	@JsonBackReference
	private CinemaRoom cinemaRoom;
}
