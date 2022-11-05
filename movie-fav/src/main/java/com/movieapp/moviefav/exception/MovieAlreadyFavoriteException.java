package com.movieapp.moviefav.exception;

public class MovieAlreadyFavoriteException extends Exception {
    public MovieAlreadyFavoriteException(String movie_is_already_in_favorite) {
        super(movie_is_already_in_favorite);
    }
}
