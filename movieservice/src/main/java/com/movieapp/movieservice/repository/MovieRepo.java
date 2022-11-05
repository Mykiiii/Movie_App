package com.movieapp.movieservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.movieapp.movieservice.model.Movie;

@Repository

public interface MovieRepo extends MongoRepository<Movie, Integer> {

}
