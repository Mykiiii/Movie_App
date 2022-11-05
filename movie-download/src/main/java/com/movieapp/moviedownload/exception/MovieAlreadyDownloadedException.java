package com.movieapp.moviedownload.exception;

public class MovieAlreadyDownloadedException extends Exception {
    public MovieAlreadyDownloadedException(String movie_is_already_downloaded) {
        super(movie_is_already_downloaded);
    }
}
