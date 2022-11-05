package com.movieapp.auth.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.auth.model.User;
import com.movieapp.auth.service.UserService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;


    @Test
    void testLogin() throws Exception {
        when(userService.findByUserIdAndPassword((Integer) any(), (String) any())).thenReturn(new User());

        User user = new User();
        user.setEmail("ayyappa.repaka@gmail.com");
        user.setIsAdmin(true);
        user.setName("Ayyappa");
        user.setPassword("password");
        user.setPhoneNumber("9201940843");
        user.setUserId(123);
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(401))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":null,\"token\":null}"));
    }


    @Test
    void testLogin2() throws Exception {
        when(userService.findByUserIdAndPassword((Integer) any(), (String) any())).thenReturn(null);

        User user = new User();
        user.setEmail("ayyappa.repaka@gmail.com");
        user.setIsAdmin(true);
        user.setName("Ayyappa");
        user.setPassword("password");
        user.setPhoneNumber("9201940843");
        user.setUserId(123);
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(401))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Invalid Credentials.\",\"token\":null}"));
    }


    @Test
    void testLogin3() throws Exception {
        when(userService.findByUserIdAndPassword((Integer) any(), (String) any())).thenReturn(new User());

        User user = new User();
        user.setEmail("ayyappa.repaka@gmail.com");
        user.setIsAdmin(true);
        user.setName("Ayyappa");
        user.setPassword(null);
        user.setPhoneNumber("9201940843");
        user.setUserId(123);
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(401))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Please fill in userId and password.\",\"token\":null}"));
    }
}

