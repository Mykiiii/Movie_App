package com.movieapp.moviedownload.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.movieapp.moviedownload.exception.MovieAlreadyDownloadedException;
import com.movieapp.moviedownload.exception.MovieNotFoundException;
import com.movieapp.moviedownload.model.Movie;
import com.movieapp.moviedownload.model.UserMovies;
import com.movieapp.moviedownload.repository.MovieDownloadRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {MovieDownloadServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MovieDownloadServiceImplTest {
    @MockBean
    private MovieDownloadRepo movieDownloadRepo;

    @Autowired
    private MovieDownloadServiceImpl movieDownloadServiceImpl;

    @MockBean
    private RestTemplate restTemplate;


    /**
     * Method under test: {@link MovieDownloadServiceImpl#updateMovie(int, int)}
     */
    @Test
    void testUpdateMovie() throws MovieAlreadyDownloadedException, MovieNotFoundException, RestClientException {
        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(new ArrayList<>());
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);

        UserMovies userMovies1 = new UserMovies();
        userMovies1.setDownloadedMovies(new ArrayList<>());
        userMovies1.setUserId(123);
        when(movieDownloadRepo.save((UserMovies) any())).thenReturn(userMovies1);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(ofResult);
        when(restTemplate.getForObject((String) any(), (Class<Movie>) any(), (Object[]) any()))
                .thenReturn(new Movie(123, "Movie Name", 1, "https://example.org/example"));
        assertTrue(movieDownloadServiceImpl.updateMovie(123, 123));
        verify(movieDownloadRepo).save((UserMovies) any());
        verify(movieDownloadRepo).findById((Integer) any());
        verify(restTemplate).getForObject((String) any(), (Class<Movie>) any(), (Object[]) any());
        assertSame(userMovies1, movieDownloadServiceImpl.userMovies);
    }

    /**
     * Method under test: {@link MovieDownloadServiceImpl#updateMovie(int, int)}
     */
    @Test
    void testUpdateMovie2() throws MovieAlreadyDownloadedException, MovieNotFoundException, RestClientException {
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(123, "Movie Name", 1, "https://example.org/example"));

        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(movieList);
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);

        UserMovies userMovies1 = new UserMovies();
        userMovies1.setDownloadedMovies(new ArrayList<>());
        userMovies1.setUserId(123);
        when(movieDownloadRepo.save((UserMovies) any())).thenReturn(userMovies1);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(ofResult);
        when(restTemplate.getForObject((String) any(), (Class<Movie>) any(), (Object[]) any()))
                .thenReturn(new Movie(123, "Movie Name", 1, "https://example.org/example"));
        assertThrows(MovieAlreadyDownloadedException.class, () -> movieDownloadServiceImpl.updateMovie(123, 123));
        verify(movieDownloadRepo).findById((Integer) any());
        verify(restTemplate).getForObject((String) any(), (Class<Movie>) any(), (Object[]) any());
    }

    @Test
    void testUpdateMovie4() throws MovieAlreadyDownloadedException, MovieNotFoundException, RestClientException {
        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(new ArrayList<>());
        userMovies.setUserId(123);
        when(movieDownloadRepo.save((UserMovies) any())).thenReturn(userMovies);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(Optional.empty());
        when(restTemplate.getForObject((String) any(), (Class<Movie>) any(), (Object[]) any()))
                .thenReturn(new Movie(123, "Movie Name", 1, "https://example.org/example"));
        assertTrue(movieDownloadServiceImpl.updateMovie(123, 123));
        verify(movieDownloadRepo).save((UserMovies) any());
        verify(movieDownloadRepo).findById((Integer) any());
        verify(restTemplate).getForObject((String) any(), (Class<Movie>) any(), (Object[]) any());
        assertEquals(1, movieDownloadServiceImpl.downloadedMovies.size());
        assertSame(userMovies, movieDownloadServiceImpl.userMovies);
    }

    /**
     * Method under test: {@link MovieDownloadServiceImpl#updateMovie(int, int)}
     */
    @Test
    void testUpdateMovie5() throws MovieAlreadyDownloadedException, MovieNotFoundException, RestClientException {
        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(new ArrayList<>());
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);

        UserMovies userMovies1 = new UserMovies();
        userMovies1.setDownloadedMovies(new ArrayList<>());
        userMovies1.setUserId(123);
        when(movieDownloadRepo.save((UserMovies) any())).thenReturn(userMovies1);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(ofResult);
        when(restTemplate.getForObject((String) any(), (Class<Movie>) any(), (Object[]) any())).thenReturn(null);
        assertThrows(MovieNotFoundException.class, () -> movieDownloadServiceImpl.updateMovie(123, 123));
        verify(movieDownloadRepo).findById((Integer) any());
        verify(restTemplate).getForObject((String) any(), (Class<Movie>) any(), (Object[]) any());
    }

    /**
     * Method under test: {@link MovieDownloadServiceImpl#updateMovie(int, int)}
     */
    @Test
    void testUpdateMovie6() throws MovieAlreadyDownloadedException, MovieNotFoundException, RestClientException {
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());

        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(movieList);
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);

        UserMovies userMovies1 = new UserMovies();
        userMovies1.setDownloadedMovies(new ArrayList<>());
        userMovies1.setUserId(123);
        when(movieDownloadRepo.save((UserMovies) any())).thenReturn(userMovies1);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(ofResult);
        when(restTemplate.getForObject((String) any(), (Class<Movie>) any(), (Object[]) any()))
                .thenReturn(new Movie(123, "Movie Name", 1, "https://example.org/example"));
        assertTrue(movieDownloadServiceImpl.updateMovie(123, 123));
        verify(movieDownloadRepo).save((UserMovies) any());
        verify(movieDownloadRepo).findById((Integer) any());
        verify(restTemplate).getForObject((String) any(), (Class<Movie>) any(), (Object[]) any());
        assertSame(userMovies1, movieDownloadServiceImpl.userMovies);
    }

    /**
     * Method under test: {@link MovieDownloadServiceImpl#viewDownloadById(int, int)}
     */
    @Test
    void testViewDownloadById() throws MovieNotFoundException {
        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(new ArrayList<>());
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(ofResult);
        assertThrows(MovieNotFoundException.class, () -> movieDownloadServiceImpl.viewDownloadById(123, 123));
        verify(movieDownloadRepo).findById((Integer) any());
    }

    /**
     * Method under test: {@link MovieDownloadServiceImpl#viewDownloadById(int, int)}
     */
    @Test
    void testViewDownloadById2() throws MovieNotFoundException {
        ArrayList<Movie> movieList = new ArrayList<>();
        Movie movie = new Movie(123, "Movie Name", 1, "https://example.org/example");

        movieList.add(movie);

        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(movieList);
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(ofResult);
        assertSame(movie, movieDownloadServiceImpl.viewDownloadById(123, 123));
        verify(movieDownloadRepo).findById((Integer) any());
        assertEquals(1, movieDownloadServiceImpl.downloadedMovies.size());
    }

    @Test
    void testViewDownloadById4() throws MovieNotFoundException {
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(Optional.empty());
        assertThrows(MovieNotFoundException.class, () -> movieDownloadServiceImpl.viewDownloadById(123, 123));
        verify(movieDownloadRepo).findById((Integer) any());
    }

    @Test
    void testViewDownloadById5() throws MovieNotFoundException {
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());

        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(movieList);
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(ofResult);
        assertThrows(MovieNotFoundException.class, () -> movieDownloadServiceImpl.viewDownloadById(123, 123));
        verify(movieDownloadRepo).findById((Integer) any());
    }

    @Test
    void testViewDownloadById7() throws MovieNotFoundException {
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        Movie movie = new Movie(123, "Movie Name", 1, "https://example.org/example");

        movieList.add(movie);

        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(movieList);
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(ofResult);
        assertSame(movie, movieDownloadServiceImpl.viewDownloadById(123, 123));
        verify(movieDownloadRepo).findById((Integer) any());
        assertEquals(2, movieDownloadServiceImpl.downloadedMovies.size());
    }

    @Test
    void testViewAllDownloads() throws MovieNotFoundException {
        UserMovies userMovies = new UserMovies();
        ArrayList<Movie> movieList = new ArrayList<>();
        userMovies.setDownloadedMovies(movieList);
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(ofResult);
        List<Movie> actualViewAllDownloadsResult = movieDownloadServiceImpl.viewAllDownloads(123);
        assertSame(movieList, actualViewAllDownloadsResult);
        assertTrue(actualViewAllDownloadsResult.isEmpty());
        verify(movieDownloadRepo).findById((Integer) any());
        assertSame(actualViewAllDownloadsResult, movieDownloadServiceImpl.downloadedMovies);
    }


    @Test
    void testRemoveAllDownloads() throws MovieNotFoundException {
        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(new ArrayList<>());
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);

        UserMovies userMovies1 = new UserMovies();
        ArrayList<Movie> movieList = new ArrayList<>();
        userMovies1.setDownloadedMovies(movieList);
        userMovies1.setUserId(123);
        when(movieDownloadRepo.save((UserMovies) any())).thenReturn(userMovies1);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(ofResult);
        movieDownloadServiceImpl.removeAllDownloads(123);
        verify(movieDownloadRepo).save((UserMovies) any());
        verify(movieDownloadRepo).findById((Integer) any());
        assertEquals(movieList, movieDownloadServiceImpl.downloadedMovies);
    }

    @Test
    void testRemoveDownloadById() throws MovieNotFoundException {
        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(new ArrayList<>());
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(ofResult);
        assertThrows(MovieNotFoundException.class, () -> movieDownloadServiceImpl.removeDownloadById(123, 123));
        verify(movieDownloadRepo).findById((Integer) any());
    }

    @Test
    void testRemoveDownloadById2() throws MovieNotFoundException {
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(123, "Movie Name", 1, "https://example.org/example"));

        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(movieList);
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);

        UserMovies userMovies1 = new UserMovies();
        ArrayList<Movie> movieList1 = new ArrayList<>();
        userMovies1.setDownloadedMovies(movieList1);
        userMovies1.setUserId(123);
        when(movieDownloadRepo.save((UserMovies) any())).thenReturn(userMovies1);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(ofResult);
        movieDownloadServiceImpl.removeDownloadById(123, 123);
        verify(movieDownloadRepo).save((UserMovies) any());
        verify(movieDownloadRepo).findById((Integer) any());
        List<Movie> movieList2 = movieDownloadServiceImpl.downloadedMovies;
        assertEquals(movieList1, movieList2);
        assertTrue(movieList2.isEmpty());
    }

    /**
     * Method under test: {@link MovieDownloadServiceImpl#removeDownloadById(int, int)}
     */
    @Test
    void testRemoveDownloadById3() throws MovieNotFoundException {
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1, "Movie Name", 1, "https://example.org/example"));

        UserMovies userMovies = new UserMovies();
        userMovies.setDownloadedMovies(movieList);
        userMovies.setUserId(123);
        Optional<UserMovies> ofResult = Optional.of(userMovies);

        UserMovies userMovies1 = new UserMovies();
        userMovies1.setDownloadedMovies(new ArrayList<>());
        userMovies1.setUserId(123);
        when(movieDownloadRepo.save((UserMovies) any())).thenReturn(userMovies1);
        when(movieDownloadRepo.findById((Integer) any())).thenReturn(ofResult);
        assertThrows(MovieNotFoundException.class, () -> movieDownloadServiceImpl.removeDownloadById(123, 123));
        verify(movieDownloadRepo).findById((Integer) any());
    }

    @Test
    void testGetMovie2() {
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(123, "Movie Name", 1, "https://example.org/example"));
        assertTrue(movieDownloadServiceImpl.getMovie(movieList, 123).isPresent());
    }

    @Test
    void testGetMovie3() {
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1, "Movie Name", 1, "https://example.org/example"));
        assertFalse(movieDownloadServiceImpl.getMovie(movieList, 123).isPresent());
    }

    @Test
    void testGetMovie4() {
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1, "Movie Name", 123, "https://example.org/example"));
        movieList.add(new Movie(123, "Movie Name", 1, "https://example.org/example"));
        assertTrue(movieDownloadServiceImpl.getMovie(movieList, 123).isPresent());
    }

    @Test
    void testGetMovie6() {
        Movie movie = mock(Movie.class);
        when(movie.getMovieId()).thenReturn(123);

        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        assertTrue(movieDownloadServiceImpl.getMovie(movieList, 123).isPresent());
        verify(movie).getMovieId();
    }
}

