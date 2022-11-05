package com.movieapp.moviefav.service;

import com.movieapp.moviefav.exception.MovieAlreadyFavoriteException;
import com.movieapp.moviefav.exception.NoFavoriteFoundException;
import com.movieapp.moviefav.exception.MovieNotFoundException;
import com.movieapp.moviefav.model.Movie;

import java.util.List;


public interface MovieFavoriteService {

	public boolean updateMovie(int userId, int movieId) throws NoFavoriteFoundException, MovieNotFoundException, MovieAlreadyFavoriteException;

	public List<Movie> viewAllFavorites(int userId) throws NoFavoriteFoundException, MovieNotFoundException;

	public void removeAllFavorites(int userId) throws MovieNotFoundException, NoFavoriteFoundException;

	public void removeFavoriteById(int userId, int movieId) throws MovieNotFoundException, NoFavoriteFoundException;

	public Movie viewFavoriteById(int userId, int movieId) throws MovieNotFoundException, NoFavoriteFoundException;

}
