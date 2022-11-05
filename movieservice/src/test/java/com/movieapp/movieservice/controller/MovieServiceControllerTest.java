package com.movieapp.movieservice.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.movieservice.model.Movie;
import com.movieapp.movieservice.service.MovieService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {MovieServiceController.class})
@ExtendWith(SpringExtension.class)
class MovieServiceControllerTest {
    @MockBean
    private MovieService movieService;

    @Autowired
    private MovieServiceController movieServiceController;

    /**
     * Method under test: {@link MovieServiceController#addMovie(Movie)}
     */
    @Test
    void testAddMovie() throws Exception {
        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("Movie Name");
        movie.setMovieReleaseYear(1);
        movie.setPosterUrl("https://example.org/example");
        when(movieService.addMovie((Movie) any())).thenReturn(movie);

        Movie movie1 = new Movie();
        movie1.setMovieId(123);
        movie1.setMovieName("Movie Name");
        movie1.setMovieReleaseYear(1);
        movie1.setPosterUrl("https://example.org/example");
        String content = (new ObjectMapper()).writeValueAsString(movie1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/movie/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(movieServiceController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"movieId\":123,\"movieName\":\"Movie Name\",\"movieReleaseYear\":1,\"posterUrl\":\"https://example.org"
                                        + "/example\"}"));
    }

    /**
     * Method under test: {@link MovieServiceController#updateMovie(Movie, int)}
     */
    @Test
    void testUpdateMovie() throws Exception {
        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("Movie Name");
        movie.setMovieReleaseYear(1);
        movie.setPosterUrl("https://example.org/example");
        when(movieService.updateMovie((Movie) any(), anyInt())).thenReturn(movie);

        Movie movie1 = new Movie();
        movie1.setMovieId(123);
        movie1.setMovieName("Movie Name");
        movie1.setMovieReleaseYear(1);
        movie1.setPosterUrl("https://example.org/example");
        String content = (new ObjectMapper()).writeValueAsString(movie1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/movie/update/{movieId}", 123)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(movieServiceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"movieId\":123,\"movieName\":\"Movie Name\",\"movieReleaseYear\":1,\"posterUrl\":\"https://example.org"
                                        + "/example\"}"));
    }

    /**
     * Method under test: {@link MovieServiceController#deleteMovieById(int)}
     */
    @Test
    void testDeleteMovieById() throws Exception {
        doNothing().when(movieService).deleteMovieById(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/movie/delete/{movieId}", 123);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieServiceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Method under test: {@link MovieServiceController#deleteMovieById(int)}
     */
    @Test
    void testDeleteMovieById2() throws Exception {
        doNothing().when(movieService).deleteMovieById(anyInt());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/movie/delete/{movieId}", 123);
        deleteResult.characterEncoding("Encoding");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieServiceController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Method under test: {@link MovieServiceController#deleteAllMovies()}
     */
    @Test
    void testDeleteAllMovies() throws Exception {
        doNothing().when(movieService).deleteAllMovies();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/movie/deleteAll");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieServiceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Method under test: {@link MovieServiceController#deleteAllMovies()}
     */
    @Test
    void testDeleteAllMovies2() throws Exception {
        doNothing().when(movieService).deleteAllMovies();
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/movie/deleteAll");
        deleteResult.characterEncoding("Encoding");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieServiceController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Method under test: {@link MovieServiceController#getAllMovies()}
     */
    @Test
    void testGetAllMovies() throws Exception {
        when(movieService.viewAllMovies()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/movie/getAll");
        MockMvcBuilders.standaloneSetup(movieServiceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link MovieServiceController#getAllMovies()}
     */
    @Test
    void testGetAllMovies2() throws Exception {
        when(movieService.viewAllMovies()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/movie/getAll");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(movieServiceController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link MovieServiceController#getMovieById(int)}
     */
    @Test
    void testGetMovieById() throws Exception {
        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("Movie Name");
        movie.setMovieReleaseYear(1);
        movie.setPosterUrl("https://example.org/example");
        when(movieService.viewMovieById(anyInt())).thenReturn(movie);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/movie/get/{movieId}", 123);
        MockMvcBuilders.standaloneSetup(movieServiceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"movieId\":123,\"movieName\":\"Movie Name\",\"movieReleaseYear\":1,\"posterUrl\":\"https://example.org"
                                        + "/example\"}"));
    }

    /**
     * Method under test: {@link MovieServiceController#internalGetMovieById(int)}
     */
    @Test
    void testInternalGetMovieById() throws Exception {
        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("Movie Name");
        movie.setMovieReleaseYear(1);
        movie.setPosterUrl("https://example.org/example");
        when(movieService.viewMovieById(anyInt())).thenReturn(movie);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/movie/internaluse/get/{movieId}",
                123);
        MockMvcBuilders.standaloneSetup(movieServiceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"movieId\":123,\"movieName\":\"Movie Name\",\"movieReleaseYear\":1,\"posterUrl\":\"https://example.org"
                                        + "/example\"}"));
    }
}

