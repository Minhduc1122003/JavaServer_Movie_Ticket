package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Cinemas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cinema {
	@Id
	private int cinemaID;
	
	private String cinemaName;
	private String address;
	
	@OneToMany(mappedBy = "cinema")
	@JsonManagedReference
	private List<Movie> moives;
	
	@OneToMany(mappedBy = "cinema")
	@JsonManagedReference
	private List<CinemaRoom> cinemaroom;
}
