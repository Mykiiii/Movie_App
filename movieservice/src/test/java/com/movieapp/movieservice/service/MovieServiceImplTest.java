package com.movieapp.movieservice.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.movieapp.movieservice.exceptions.MovieAlreadyExistsException;
import com.movieapp.movieservice.exceptions.MovieNotFoundException;
import com.movieapp.movieservice.model.Movie;
import com.movieapp.movieservice.repository.MovieRepo;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MovieServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MovieServiceImplTest {
    @MockBean
    private MovieRepo movieRepo;

    @Autowired
    private MovieServiceImpl movieServiceImpl;


    @Test
    void testAddMovie() throws MovieAlreadyExistsException {
        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("KGF-3");
        movie.setMovieReleaseYear(2025);
        movie.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");

        Movie movie1 = new Movie();
        movie1.setMovieId(123);
        movie1.setMovieName("KGF-3");
        movie1.setMovieReleaseYear(2025);
        movie1.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");
        Optional<Movie> ofResult = Optional.of(movie1);
        when(movieRepo.save((Movie) any())).thenReturn(movie);
        when(movieRepo.findById((Integer) any())).thenReturn(ofResult);

        Movie movie2 = new Movie();
        movie2.setMovieId(123);
        movie2.setMovieName("KGF-3");
        movie2.setMovieReleaseYear(2025);
        movie2.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");
        assertThrows(MovieAlreadyExistsException.class, () -> movieServiceImpl.addMovie(movie2));
        verify(movieRepo).findById((Integer) any());
    }


    @Test
    void testAddMovie2() throws MovieAlreadyExistsException {
        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("KGF-3");
        movie.setMovieReleaseYear(2025);
        movie.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");
        when(movieRepo.save((Movie) any())).thenReturn(movie);
        when(movieRepo.findById((Integer) any())).thenReturn(Optional.empty());

        Movie movie1 = new Movie();
        movie1.setMovieId(123);
        movie1.setMovieName("KGF-3");
        movie1.setMovieReleaseYear(2025);
        movie1.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");
        assertSame(movie, movieServiceImpl.addMovie(movie1));
        verify(movieRepo).save((Movie) any());
        verify(movieRepo).findById((Integer) any());
    }


    @Test
    void testUpdateMovie() throws MovieNotFoundException {
        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("KGF-3");
        movie.setMovieReleaseYear(2025);
        movie.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");
        Optional<Movie> ofResult = Optional.of(movie);

        Movie movie1 = new Movie();
        movie1.setMovieId(123);
        movie1.setMovieName("KGF-3");
        movie1.setMovieReleaseYear(2025);
        movie1.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");
        when(movieRepo.save((Movie) any())).thenReturn(movie1);
        when(movieRepo.findById((Integer) any())).thenReturn(ofResult);

        Movie movie2 = new Movie();
        movie2.setMovieId(123);
        movie2.setMovieName("KGF-3");
        movie2.setMovieReleaseYear(2025);
        movie2.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");
        assertSame(movie1, movieServiceImpl.updateMovie(movie2, 123));
        verify(movieRepo).save((Movie) any());
        verify(movieRepo).findById((Integer) any());
    }


    @Test
    void testUpdateMovie2() throws MovieNotFoundException {
        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("KGF-3");
        movie.setMovieReleaseYear(2025);
        movie.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");
        when(movieRepo.save((Movie) any())).thenReturn(movie);
        when(movieRepo.findById((Integer) any())).thenReturn(Optional.empty());

        Movie movie1 = new Movie();
        movie1.setMovieId(123);
        movie1.setMovieName("KGF-3");
        movie1.setMovieReleaseYear(2025);
        movie1.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");
        assertThrows(MovieNotFoundException.class, () -> movieServiceImpl.updateMovie(movie1, 123));
        verify(movieRepo).findById((Integer) any());
    }


    @Test
    void testViewMovieById() throws MovieNotFoundException {
        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("KGF-3");
        movie.setMovieReleaseYear(2025);
        movie.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");
        Optional<Movie> ofResult = Optional.of(movie);
        when(movieRepo.findById((Integer) any())).thenReturn(ofResult);
        assertSame(movie, movieServiceImpl.viewMovieById(123));
        verify(movieRepo).findById((Integer) any());
    }


    @Test
    void testViewMovieById2() throws MovieNotFoundException {
        when(movieRepo.findById((Integer) any())).thenReturn(Optional.empty());
        assertThrows(MovieNotFoundException.class, () -> movieServiceImpl.viewMovieById(123));
        verify(movieRepo).findById((Integer) any());
    }


    @Test
    void testViewAllMovies() throws MovieNotFoundException {
        ArrayList<Movie> movieList = new ArrayList<>();
        when(movieRepo.findAll()).thenReturn(movieList);
        List<Movie> actualViewAllMoviesResult = movieServiceImpl.viewAllMovies();
        assertSame(movieList, actualViewAllMoviesResult);
        assertTrue(actualViewAllMoviesResult.isEmpty());
        verify(movieRepo).findAll();
    }


    @Test
    void testDeleteMovieById() throws MovieNotFoundException {
        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("KGF-3");
        movie.setMovieReleaseYear(2025);
        movie.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");
        Optional<Movie> ofResult = Optional.of(movie);
        doNothing().when(movieRepo).deleteById((Integer) any());
        when(movieRepo.findById((Integer) any())).thenReturn(ofResult);
        movieServiceImpl.deleteMovieById(123);
        verify(movieRepo).findById((Integer) any());
        verify(movieRepo).deleteById((Integer) any());
    }


    @Test
    void testDeleteMovieById2() throws MovieNotFoundException {
        doNothing().when(movieRepo).deleteById((Integer) any());
        when(movieRepo.findById((Integer) any())).thenReturn(Optional.empty());
        assertThrows(MovieNotFoundException.class, () -> movieServiceImpl.deleteMovieById(123));
        verify(movieRepo).findById((Integer) any());
    }


    @Test
    void testDeleteAllMovies() throws MovieNotFoundException {

        assertThrows(MovieNotFoundException.class, () -> (new MovieServiceImpl()).deleteAllMovies());
    }
}

