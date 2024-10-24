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
@Table(name = "Favourite")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Favourite {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdFavourite")
	private int idFavourite;
	
	@ManyToOne
	@JoinColumn(name = "MovieID", referencedColumnName = "MovieID", nullable = false)
	@JsonBackReference
	private Movie movie;
	
	@ManyToOne
	@JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false)
	@JsonBackReference
	private User user;
	
	public int getMovieId() {
	    return movie.getMovieId(); // Assuming the Movie entity has a method getMovieId()
	}

	public int getUserId() {
	    return user.getUserId(); // Assuming the User entity has a method getUserId()
	}

}
