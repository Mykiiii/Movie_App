package com.movieapp.moviefav.service;

import com.movieapp.moviefav.exception.MovieAlreadyFavoriteException;
import com.movieapp.moviefav.exception.NoFavoriteFoundException;
import com.movieapp.moviefav.exception.MovieNotFoundException;
import com.movieapp.moviefav.model.Movie;
import com.movieapp.moviefav.model.UserMovies;
import com.movieapp.moviefav.repository.MovieFavoriteRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MovieFavoriteServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MovieFavoriteServiceImplTest {
    @MockBean
    private MovieFavoriteRepo movieFavoriteRepo;

    @Autowired
    private MovieFavoriteServiceImpl movieFavoriteServiceImpl;

    @MockBean
    private RestTemplate restTemplate;


    @Test
    void testUpdateMovie() throws MovieAlreadyFavoriteException, MovieNotFoundException, RestClientException {
        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(new ArrayList<>());
        userMovies.setFavMovies(new ArrayList<>());
        userMovies.setUserId(123);

        UserMovies userMovies1 = new UserMovies();
        userMovies1.setDownloadedMovies(new ArrayList<>());
        userMovies1.setFavMovies(new ArrayList<>());
        userMovies1.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies1);
        when(movieFavoriteRepo.save((UserMovies) any())).thenReturn(userMovies);
        when(movieFavoriteRepo.findById((Integer) any())).thenReturn(ofResult);

        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("KGF-2");
        movie.setMovieReleaseYear(2022);
        movie.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");
        when(restTemplate.getForObject((String) any(), (Class<Movie>) any(), (Object[]) any())).thenReturn(movie);
        assertTrue(movieFavoriteServiceImpl.updateMovie(123, 123));
        verify(movieFavoriteRepo).save((UserMovies) any());
        verify(movieFavoriteRepo).findById((Integer) any());
        verify(restTemplate).getForObject((String) any(), (Class<Movie>) any(), (Object[]) any());
        assertEquals(1, movieFavoriteServiceImpl.favMovies.size());
        assertSame(userMovies, movieFavoriteServiceImpl.userMovies);
    }


    @Test
    void testUpdateMovie2() throws MovieAlreadyFavoriteException, MovieNotFoundException, RestClientException {
        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(new ArrayList<>());
        userMovies.setFavMovies(new ArrayList<>());
        userMovies.setUserId(123);

        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("KGF-2");
        movie.setMovieReleaseYear(2022);
        movie.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");

        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(movie);

        UserMovies userMovies1 = new UserMovies();
        userMovies1.setDownloadedMovies(new ArrayList<>());
        userMovies1.setFavMovies(movieList);
        userMovies1.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies1);
        when(movieFavoriteRepo.save((UserMovies) any())).thenReturn(userMovies);
        when(movieFavoriteRepo.findById((Integer) any())).thenReturn(ofResult);

        Movie movie1 = new Movie();
        movie1.setMovieId(123);
        movie1.setMovieName("KGF-2");
        movie1.setMovieReleaseYear(2022);
        movie1.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");
        when(restTemplate.getForObject((String) any(), (Class<Movie>) any(), (Object[]) any())).thenReturn(movie1);
        assertThrows(MovieAlreadyFavoriteException.class, () -> movieFavoriteServiceImpl.updateMovie(123, 123));
        verify(movieFavoriteRepo).findById((Integer) any());
        verify(restTemplate).getForObject((String) any(), (Class<Movie>) any(), (Object[]) any());
    }


    @Test
    void testViewFavoriteById2() throws NoFavoriteFoundException, MovieNotFoundException {
        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("KGF-2");
        movie.setMovieReleaseYear(2022);
        movie.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");

        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(movie);

        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(new ArrayList<>());
        userMovies.setFavMovies(movieList);
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);
        when(movieFavoriteRepo.findById((Integer) any())).thenReturn(ofResult);
        assertSame(movie, movieFavoriteServiceImpl.viewFavoriteById(123, 123));
        verify(movieFavoriteRepo).findById((Integer) any());
        assertEquals(1, movieFavoriteServiceImpl.favMovies.size());
    }


    @Test
    void testViewAllFavorites() throws NoFavoriteFoundException, MovieNotFoundException {
        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(new ArrayList<>());
        ArrayList<Movie> movieList = new ArrayList<>();
        userMovies.setFavMovies(movieList);
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);
        when(movieFavoriteRepo.findById((Integer) any())).thenReturn(ofResult);
        List<Movie> actualViewAllFavoritesResult = movieFavoriteServiceImpl.viewAllFavorites(123);
        assertSame(movieList, actualViewAllFavoritesResult);
        assertTrue(actualViewAllFavoritesResult.isEmpty());
        verify(movieFavoriteRepo).findById((Integer) any());
        assertSame(actualViewAllFavoritesResult, movieFavoriteServiceImpl.favMovies);
    }

    @Test
    void testRemoveAllFavorites() throws NoFavoriteFoundException, MovieNotFoundException {
        UserMovies userMovies = new UserMovies();
        ArrayList<Movie> movieList = new ArrayList<>();
        userMovies.setDownloadedMovies(movieList);
        userMovies.setFavMovies(new ArrayList<>());
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);

        UserMovies userMovies1 = new UserMovies();
        userMovies1.setDownloadedMovies(new ArrayList<>());
        userMovies1.setFavMovies(new ArrayList<>());
        userMovies1.setUserId(123);
        when(movieFavoriteRepo.save((UserMovies) any())).thenReturn(userMovies1);
        when(movieFavoriteRepo.findById((Integer) any())).thenReturn(ofResult);
        movieFavoriteServiceImpl.removeAllFavorites(123);
        verify(movieFavoriteRepo).save((UserMovies) any());
        verify(movieFavoriteRepo).findById((Integer) any());
        assertEquals(movieList, movieFavoriteServiceImpl.favMovies);
    }


    @Test
    void testRemoveFavoriteById2() throws NoFavoriteFoundException, MovieNotFoundException {
        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("KGF-2");
        movie.setMovieReleaseYear(2022);
        movie.setPosterUrl("https://en.wikipedia.org/wiki/K.G.F:_Chapter_1");

        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(movie);

        UserMovies userMovies = new UserMovies();
        ArrayList<Movie> movieList1 = new ArrayList<>();
        userMovies.setDownloadedMovies(movieList1);
        userMovies.setFavMovies(movieList);
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);

        UserMovies userMovies1 = new UserMovies();
        userMovies1.setDownloadedMovies(new ArrayList<>());
        userMovies1.setFavMovies(new ArrayList<>());
        userMovies1.setUserId(123);
        when(movieFavoriteRepo.save((UserMovies) any())).thenReturn(userMovies1);
        when(movieFavoriteRepo.findById((Integer) any())).thenReturn(ofResult);
        movieFavoriteServiceImpl.removeFavoriteById(123, 123);
        verify(movieFavoriteRepo).save((UserMovies) any());
        verify(movieFavoriteRepo).findById((Integer) any());
        List<Movie> movieList2 = movieFavoriteServiceImpl.favMovies;
        assertEquals(movieList1, movieList2);
        assertTrue(movieList2.isEmpty());
    }

}

