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
@Table(name = "CinemaRoom")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CinemaRoomID")
	private int cinemaRoomId;
	
	@OneToMany(mappedBy = "cinemaRoom")
	@JsonManagedReference
	private List<Showtime> showtimes;
	
	@OneToMany(mappedBy = "cinemaRoom")
	@JsonBackReference
	private List<Seat> seats;
	
	@ManyToOne
	@JoinColumn(name = "CinemaID", referencedColumnName = "CinemaID", nullable = false)
	@JsonBackReference
	private Cinema cinema;
}
