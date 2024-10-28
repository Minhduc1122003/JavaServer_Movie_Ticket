package com.example.demo.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	@Column(name = "IdGenre")
    private Integer idGenre;

    private String genreName;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "genre-moviegenre")
    private List<MovieGenre> movieGenre;
}
