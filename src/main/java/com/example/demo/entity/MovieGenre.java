package com.example.demo.entity;

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
@Table(name = "MovieGenre")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieGenre {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdmovieGenre")
    private int idmovieGenre;
	
	@ManyToOne
	@JoinColumn(name = "MovieID", referencedColumnName = "MovieID", nullable = false)
	private Movie movie;

	@ManyToOne
    @JoinColumn(name = "IdGenre", referencedColumnName = "IdGenre", nullable = false)
    private Genre genre;
}
