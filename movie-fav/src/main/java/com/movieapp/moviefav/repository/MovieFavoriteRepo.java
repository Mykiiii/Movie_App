package com.movieapp.moviefav.repository;

import com.movieapp.moviefav.model.UserMovies;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieFavoriteRepo extends MongoRepository<UserMovies, Integer>{
	
}
