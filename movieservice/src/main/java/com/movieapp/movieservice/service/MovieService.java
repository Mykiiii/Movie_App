package com.movieapp.movieservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.movieapp.movieservice.exceptions.MovieAlreadyExistsException;
import com.movieapp.movieservice.exceptions.MovieNotFoundException;
import com.movieapp.movieservice.model.Movie;

@Service
public interface MovieService {

	Movie addMovie(Movie movie) throws MovieAlreadyExistsException;
	Movie updateMovie(Movie movie, int movieId) throws MovieNotFoundException;
	Movie viewMovieById(int movieId) throws MovieNotFoundException;
	List<Movie> viewAllMovies() throws MovieNotFoundException;
	void deleteMovieById(int movieId) throws MovieNotFoundException;
	boolean deleteAllMovies() throws MovieNotFoundException;
}
