package com.movieapp.moviedownload.controller;

import com.movieapp.moviedownload.model.Movie;
import com.movieapp.moviedownload.service.MovieDownloadService;
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

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MovieDownloadController.class})
@ExtendWith(SpringExtension.class)
class MovieDownloadControllerTest {
    @Autowired
    private MovieDownloadController movieDownloadController;

    @MockBean
    private MovieDownloadService movieDownloadService;

    @Test
    void testDownloadMovie3() throws Exception {
        Object[] obj = new Object[]{123, 123};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/download/movie/{userId}/{movieId}",
                obj);
        Object[] controllers = new Object[]{movieDownloadController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

    }

    @Test
    void testRemoveAllDownloads3() throws Exception {
        Object[] obj = new Object[]{123};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/download/movie/deleteAll/{userId}", obj);
        Object[] controllers = new Object[]{movieDownloadController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

    }

    @Test
    void testRemoveDownloadById3() throws Exception {
        Object[] obj = new Object[]{123, 123};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/download/movie/delete/{userId}/{movieId}", obj);
        Object[] controllers = new Object[]{movieDownloadController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

    }

    @Test
    void testViewAllDownloads3() throws Exception {
        Object[] obj = new Object[]{123};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/download/movie/getAll/{userId}",
                obj);
        Object[] controllers = new Object[]{movieDownloadController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

    }

    @Test
    void testViewDownloadById() throws Exception {
        when(movieDownloadService.viewDownloadById(anyInt(), anyInt()))
                .thenReturn(new Movie(123, "Movie Name", 1, "https://example.org/example"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/download/movie/get/{userId}/{movieId}", 123, 123);
        MockMvcBuilders.standaloneSetup(movieDownloadController)
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
    void testViewAllDownloads() throws Exception {
        when(movieDownloadService.viewAllDownloads(anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/download/movie/getAll/{userId}",
                123);
        MockMvcBuilders.standaloneSetup(movieDownloadController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testViewAllDownloads2() throws Exception {
        when(movieDownloadService.viewAllDownloads(anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/download/movie/getAll/{userId}", 123);
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(movieDownloadController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testRemoveDownloadById() throws Exception {
        doNothing().when(movieDownloadService).removeDownloadById(anyInt(), anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/download/movie/delete/{userId}/{movieId}", 123, 123);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieDownloadController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testRemoveDownloadById2() throws Exception {
        doNothing().when(movieDownloadService).removeDownloadById(anyInt(), anyInt());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders
                .delete("/api/download/movie/delete/{userId}/{movieId}", 123, 123);
        deleteResult.characterEncoding("Encoding");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieDownloadController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testDownloadMovie() throws Exception {
        when(movieDownloadService.updateMovie(anyInt(), anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/download/movie/{userId}/{movieId}",
                123, 123);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieDownloadController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testRemoveAllDownloads() throws Exception {
        doNothing().when(movieDownloadService).removeAllDownloads(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/download/movie/deleteAll/{userId}", 123);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieDownloadController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testRemoveAllDownloads2() throws Exception {
        doNothing().when(movieDownloadService).removeAllDownloads(anyInt());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/download/movie/deleteAll/{userId}",
                123);
        deleteResult.characterEncoding("Encoding");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieDownloadController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testDownloadMovie2() throws Exception {
        when(movieDownloadService.updateMovie(anyInt(), anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/download/movie/{userId}/{movieId}",
                123, 123);
        postResult.characterEncoding("Encoding");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieDownloadController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testViewDownloadById2() throws Exception {
        Object[] obj = new Object[]{123, 123};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/download/movie/get/{userId}/{movieId}", obj);
        Object[] controllers = new Object[]{movieDownloadController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

    }
}

