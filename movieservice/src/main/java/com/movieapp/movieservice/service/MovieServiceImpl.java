package com.movieapp.movieservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieapp.movieservice.exceptions.MovieAlreadyExistsException;
import com.movieapp.movieservice.exceptions.MovieNotFoundException;
import com.movieapp.movieservice.model.Movie;
import com.movieapp.movieservice.repository.MovieRepo;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepo movieRepo;


	private Movie res = null;

	@Override
	public Movie addMovie(Movie movie) throws MovieAlreadyExistsException {

		Optional<Movie> mv = movieRepo.findById(movie.getMovieId());
		if (mv.isEmpty()) {
			res = movieRepo.save(movie);
			return res;
		} else {
			// TODO: handle exception
			throw new MovieAlreadyExistsException("Movie Already exists with id = " + movie.getMovieId());
		}

	}

	@Override
	public Movie updateMovie(Movie movie, int movieId) throws MovieNotFoundException {
		Optional<Movie> mv = movieRepo.findById(movieId);
		if (mv.isPresent()) {
			res = movieRepo.save(movie);
			return res;
		} else {
			throw new MovieNotFoundException("Movie doesn't exist with id = " + movieId);
		}
	}

	@Override
	public Movie viewMovieById(int movieId) throws MovieNotFoundException {
		// TODO Auto-generated method stub
		Optional<Movie> optMv = movieRepo.findById(movieId);
		if (optMv.isPresent()) {
			return optMv.get();
		} else {
			throw new MovieNotFoundException("Movie doesn't exist with id = " + movieId);
		}
	}

	@Override
	public List<Movie> viewAllMovies() throws MovieNotFoundException {
		// TODO Auto-generated method stub
		List<Movie> movies = movieRepo.findAll();
		if (movies != null && !(movies.isEmpty())) {
			return movies;
		} else {
			throw new MovieNotFoundException("No Movies found");
		}
	}

	@Override
	public void deleteMovieById(int movieId) throws MovieNotFoundException {

		Optional<Movie> mv = movieRepo.findById(movieId);
		if (mv.isPresent()) {
			movieRepo.deleteById(movieId);
		} else {
			throw new MovieNotFoundException("Movie Not found with Id = " + movieId);
		}

	}

	@Override
	public boolean deleteAllMovies() throws MovieNotFoundException {

//		try {
			List<Movie> movies = movieRepo.findAll();
			if (movies != null && !(movies.isEmpty())) {
				movieRepo.deleteAll();
				return true;
			}else{
				throw new MovieNotFoundException("Movies Not Found");
			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			throw new MovieNotFoundException("No Movies Found");
//		}
	}
}
