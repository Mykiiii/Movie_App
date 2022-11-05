package com.movieapp.moviedownload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class UserMovies {

	@Id
	private int userId;

	private List<Movie> downloadedMovies;
	private List<Movie> favMovies;

}
