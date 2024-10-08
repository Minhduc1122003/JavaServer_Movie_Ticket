package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Genre")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Genre {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGenre;

    private String genreName;

    @ManyToMany(mappedBy = "genres")
    @JsonBackReference
    private List<Movie> movies;
}
