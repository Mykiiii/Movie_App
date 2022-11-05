package com.movieapp.moviedownload.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.movieapp.moviedownload.model.UserMovies;

@Repository
public interface MovieDownloadRepo extends MongoRepository<UserMovies, Integer>{
	
}
