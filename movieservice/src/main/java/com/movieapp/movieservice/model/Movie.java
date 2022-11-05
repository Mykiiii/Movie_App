package com.movieapp.movieservice.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

	@Id
	private int movieId;

	private String movieName;
	private int movieReleaseYear;
	private String posterUrl;

}
