package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Movies")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer movieID;
    private Integer cinemaID;
    private String title;
    private String description;
    private Integer duration;
    private Date releaseDate;
    private String posterUrl;
    private String trailerUrl;
    private String age;
    private Boolean subTitle;
    private Boolean voiceover;
    private String statusMovie;
    private Double price;
    private Boolean isDelete;
    
    @ManyToMany
    @JoinTable(
        name = "MovieGenre",
        joinColumns = @JoinColumn(name = "MovieID"),
        inverseJoinColumns = @JoinColumn(name = "IdGenre")
    )
    @JsonManagedReference
    private List<Genre> genres;

    @OneToMany(mappedBy = "movie")
    @JsonManagedReference
    private List<Rate> rates;


}
