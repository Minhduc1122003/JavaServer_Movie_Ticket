package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.MovieDTO;
import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;

@Controller
public class HomeController {
	@Autowired
    private MovieService movieService;

	// localhost:9011/
	@GetMapping("/")
    public String index(Model model) {
        List<MovieDTO> movies = movieService.getAllMoviesDTO();
        model.addAttribute("movies", movies);
        return "index";
    }
}