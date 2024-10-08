package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "MovieID", nullable = false)
    private int movieId;

    @Column(name = "IdGenre", nullable = false)
    private int idGenre;
}
