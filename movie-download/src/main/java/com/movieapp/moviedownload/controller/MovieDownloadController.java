package com.movieapp.moviedownload.controller;

import java.util.List;

import com.movieapp.moviedownload.model.UserMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.movieapp.moviedownload.model.Movie;
import com.movieapp.moviedownload.service.MovieDownloadService;

@RestController
@RequestMapping("api/download/movie")
public class MovieDownloadController {

	@Autowired
	private MovieDownloadService downloadService;

	private Movie mv;


//	@PostMapping("/save")
//	public ResponseEntity<UserMovies> createUserMovies()

	@PostMapping("/{userId}/{movieId}")
	public ResponseEntity<?> downloadMovie(@PathVariable int userId, @PathVariable int movieId) {

		try {
			boolean flag = downloadService.updateMovie(userId, movieId);
			return new ResponseEntity<Boolean>(flag, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}

	}
	
	@GetMapping("/get/{userId}/{movieId}")
	public ResponseEntity<?> viewDownloadById(@PathVariable int userId, @PathVariable int movieId) {
		try {
			Movie mv = downloadService.viewDownloadById(userId,movieId);
			return new ResponseEntity<Movie>(mv, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/getAll/{userId}")
	public ResponseEntity<?> viewAllDownloads(@PathVariable int userId) {
		try {
			List<Movie> movies = downloadService.viewAllDownloads(userId);
			return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping("/delete/{userId}/{movieId}")
	public ResponseEntity<?> removeDownloadById(@PathVariable int userId, @PathVariable int movieId) {
		try {
			downloadService.removeDownloadById(userId,movieId);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping("/deleteAll/{userId}")
	public ResponseEntity<?> removeAllDownloads(@PathVariable int userId) {
		try {
			downloadService.removeAllDownloads(userId);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

}
