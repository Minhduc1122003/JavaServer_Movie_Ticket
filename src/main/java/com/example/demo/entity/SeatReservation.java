package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SeatReservation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatReservation {
	@Id
	@Column(name = "ReservationID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reservationID;
	
	@ManyToOne
	@JoinColumn(name = "ShowtimeID", referencedColumnName = "ShowtimeID")
	@JsonBackReference
	private Showtime showtime;
	
	@ManyToOne
	@JoinColumn(name = "SeatID", referencedColumnName = "SeatID")
	@JsonBackReference
	private Seat seat;
}
