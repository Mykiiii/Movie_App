package com.movieapp.moviefav.controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.movieapp.moviefav.model.Movie;
import com.movieapp.moviefav.service.MovieFavoriteService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {MovieFavoriteController.class})
@ExtendWith(SpringExtension.class)
class MovieFavoriteControllerTest {
    @Autowired
    private MovieFavoriteController movieFavoriteController;

    @MockBean
    private MovieFavoriteService movieFavoriteService;

    @Test
    void testMakeMovieFavorite3() throws Exception {
        Object[] obj = new Object[]{123, 123};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/favorite/movie/{userId}/{movieId}",
                obj);
        Object[] controllers = new Object[]{movieFavoriteController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();


        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

    }

    @Test
    void testRemoveAllFavorites3() throws Exception {
        Object[] obj = new Object[]{123};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/favorite/movie/deleteAll/{userId}", obj);
        Object[] controllers = new Object[]{movieFavoriteController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    }

    @Test
    void testRemoveFavoriteById3() throws Exception {
        Object[] obj = new Object[]{123, 123};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/favorite/movie/delete/{userId}/{movieId}", obj);
        Object[] controllers = new Object[]{movieFavoriteController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    }

    @Test
    void testViewAllFavorites3() throws Exception {
        Object[] obj = new Object[]{123};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/favorite/movie/getAll/{userId}",
                obj);
        Object[] controllers = new Object[]{movieFavoriteController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    }

    @Test
    void testViewFavoriteById() throws Exception {
        Movie movie = new Movie();
        movie.setMovieId(123);
        movie.setMovieName("Movie Name");
        movie.setMovieReleaseYear(1);
        movie.setPosterUrl("https://example.org/example");
        when(movieFavoriteService.viewFavoriteById(anyInt(), anyInt())).thenReturn(movie);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/favorite/movie/get/{userId}/{movieId}", 123, 123);
        MockMvcBuilders.standaloneSetup(movieFavoriteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"movieId\":123,\"movieName\":\"Movie Name\",\"movieReleaseYear\":1,\"posterUrl\":\"https://example.org"
                                        + "/example\"}"));
    }

    @Test
    void testViewAllFavorites() throws Exception {
        when(movieFavoriteService.viewAllFavorites(anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/favorite/movie/getAll/{userId}",
                123);
        MockMvcBuilders.standaloneSetup(movieFavoriteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testViewAllFavorites2() throws Exception {
        when(movieFavoriteService.viewAllFavorites(anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/favorite/movie/getAll/{userId}", 123);
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(movieFavoriteController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testRemoveFavoriteById() throws Exception {
        doNothing().when(movieFavoriteService).removeFavoriteById(anyInt(), anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/favorite/movie/delete/{userId}/{movieId}", 123, 123);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieFavoriteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testRemoveFavoriteById2() throws Exception {
        doNothing().when(movieFavoriteService).removeFavoriteById(anyInt(), anyInt());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders
                .delete("/api/favorite/movie/delete/{userId}/{movieId}", 123, 123);
        deleteResult.characterEncoding("Encoding");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieFavoriteController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testMakeMovieFavorite() throws Exception {
        when(movieFavoriteService.updateMovie(anyInt(), anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/favorite/movie/{userId}/{movieId}",
                123, 123);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieFavoriteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testRemoveAllFavorites() throws Exception {
        doNothing().when(movieFavoriteService).removeAllFavorites(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/favorite/movie/deleteAll/{userId}", 123);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieFavoriteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testRemoveAllFavorites2() throws Exception {
        doNothing().when(movieFavoriteService).removeAllFavorites(anyInt());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/favorite/movie/deleteAll/{userId}",
                123);
        deleteResult.characterEncoding("Encoding");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieFavoriteController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testMakeMovieFavorite2() throws Exception {
        when(movieFavoriteService.updateMovie(anyInt(), anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/favorite/movie/{userId}/{movieId}",
                123, 123);
        postResult.characterEncoding("Encoding");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieFavoriteController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testViewFavoriteById2() throws Exception {
        Object[] obj = new Object[]{123, 123};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/favorite/movie/get/{userId}/{movieId}", obj);
        Object[] controllers = new Object[]{movieFavoriteController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    }
}

