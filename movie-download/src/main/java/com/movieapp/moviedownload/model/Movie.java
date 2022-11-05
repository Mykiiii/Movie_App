package com.movieapp.moviedownload.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

//@Document
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

	private int movieId;
	private String movieName;
	private int movieReleaseYear;
	private String posterUrl;
//	private boolean isFav;
//	private boolean isDownloaded;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Movie movie = (Movie) o;
		return movieId == movie.movieId;
	}

}
