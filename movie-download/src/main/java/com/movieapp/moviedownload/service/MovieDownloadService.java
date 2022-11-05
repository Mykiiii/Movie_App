package com.movieapp.moviedownload.service;

import java.util.List;

import com.movieapp.moviedownload.exception.MovieAlreadyDownloadedException;
import com.movieapp.moviedownload.exception.MovieNotFoundException;


import com.movieapp.moviedownload.model.Movie;

public interface MovieDownloadService {

	public boolean updateMovie(int userId, int movieId) throws MovieNotFoundException, MovieAlreadyDownloadedException;

	public List<Movie> viewAllDownloads(int userId) throws MovieNotFoundException;

	public void removeAllDownloads(int userId) throws MovieNotFoundException;

	public void removeDownloadById(int userId, int movieId) throws MovieNotFoundException;

	public Movie viewDownloadById(int userId, int movieId) throws MovieNotFoundException;

}
