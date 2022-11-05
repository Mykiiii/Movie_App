package com.movieapp.moviefav.controller;


import com.movieapp.moviefav.model.Movie;
import com.movieapp.moviefav.service.MovieFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/favorite/movie")
public class MovieFavoriteController {

	@Autowired
	private MovieFavoriteService favoriteService;

	private Movie mv;

	@PostMapping("/{userId}/{movieId}")
	public ResponseEntity<?> makeMovieFavorite(@PathVariable int userId, @PathVariable int movieId) {

		try {
			boolean flag = favoriteService.updateMovie(userId, movieId);
			return new ResponseEntity<Boolean>(flag, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}

	}

	@GetMapping("/get/{userId}/{movieId}")
	public ResponseEntity<?> viewFavoriteById(@PathVariable int userId, @PathVariable int movieId) {
		try {
			Movie mv = favoriteService.viewFavoriteById(userId,movieId);
			return new ResponseEntity<Movie>(mv, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/getAll/{userId}")
	public ResponseEntity<?> viewAllFavorites(@PathVariable int userId) {
		try {
			List<Movie> movies = favoriteService.viewAllFavorites(userId);
			return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/delete/{userId}/{movieId}")
	public ResponseEntity<?> removeFavoriteById(@PathVariable int userId, @PathVariable int movieId) {
		try {
			favoriteService.removeFavoriteById(userId,movieId);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/deleteAll/{userId}")
	public ResponseEntity<?> removeAllFavorites(@PathVariable int userId) {
		try {
			favoriteService.removeAllFavorites(userId);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

}
