package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "Rate")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRate;

    private String content;
    private float rating;
    
    @ManyToOne
    @JoinColumn(name = "MovieID", nullable = false)
    @JsonBackReference(value = "movie-rate")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    @JsonBackReference(value = "user-rate")
    private User user;
}
