package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
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
@Table(name = "Seats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Seat {
	@Id
	@Column(name = "SeatID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int seatID;
	
	private String chairCode;
	private boolean defectiveChair;
	
	@OneToMany(mappedBy = "seat")
	@JsonManagedReference
	private List<TicketSeat> ticketseats;
	
	@OneToMany(mappedBy = "seat")
	@JsonManagedReference
	private List<SeatReservation> seatReservation;
	
	@ManyToOne
	@JoinColumn(name = "CinemaRoomID", referencedColumnName = "CinemaRoomID", nullable = false)
	@JsonBackReference
	private CinemaRoom cinemaRoom;
}
