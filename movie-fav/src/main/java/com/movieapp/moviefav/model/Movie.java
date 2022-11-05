package com.movieapp.moviefav.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



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
//	private boolean isFav;
//	private boolean isDownloaded;

}
