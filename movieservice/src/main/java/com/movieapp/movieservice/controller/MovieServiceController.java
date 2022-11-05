package com.movieapp.movieservice.controller;

import com.movieapp.movieservice.model.Movie;
import com.movieapp.movieservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieServiceController {

    @Autowired
    private MovieService movieService;

    private Movie mv;

    @PostMapping("/add")
    public ResponseEntity<?> addMovie(@RequestBody Movie movie){

        try {
            mv = movieService.addMovie(movie);
            if(mv==null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }else{
                return new ResponseEntity<Movie>(mv, HttpStatus.CREATED);
            }
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/get/{movieId}")
    public ResponseEntity<?> getMovieById(@PathVariable int movieId){
        try {
            mv = movieService.viewMovieById(movieId);
            return new ResponseEntity<Movie>(mv, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/internaluse/get/{movieId}")
    public ResponseEntity<?> internalGetMovieById(@PathVariable int movieId){
        try {
            mv = movieService.viewMovieById(movieId);
            return new ResponseEntity<Movie>(mv, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllMovies(){
        try {
            List<Movie> movies = movieService.viewAllMovies();
            return new ResponseEntity<List>(movies, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{movieId}")
    public ResponseEntity<?> updateMovie(@RequestBody Movie movie, @PathVariable int movieId){
        try {
            mv = movieService.updateMovie(movie,movieId);
            if(mv==null){
                return new ResponseEntity<String>("Movie not Updated Successfully",HttpStatus.CONFLICT);
            }
            return new ResponseEntity<Movie>(mv, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<?> deleteMovieById(@PathVariable int movieId){
        try {
            movieService.deleteMovieById(movieId);
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllMovies(){
        try {
            boolean status = movieService.deleteAllMovies();
            if(status)
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            return new ResponseEntity<String>("Movies not Found", HttpStatus.NOT_FOUND);

        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
