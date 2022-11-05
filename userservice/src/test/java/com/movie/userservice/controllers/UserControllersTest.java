package com.movie.userservice.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.userservice.model.User;
import com.movie.userservice.service.UserService;
import com.movie.userservice.util.exception.UserAlreadyExistException;
import com.movie.userservice.util.exception.UserNotFoundException;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserControllers.class})
@ExtendWith(SpringExtension.class)
class UserControllersTest {
    @Autowired
    private UserControllers userControllers;

    @MockBean
    private UserService userService;


    @Test
    void testGetAllUser() throws Exception {
        when(userService.getAllUser()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/viewAll");
        MockMvcBuilders.standaloneSetup(userControllers)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGetAllUser2() throws Exception {
        when(userService.getAllUser()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/user/viewAll");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userControllers)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testUpdateUser() throws Exception {
        User user = new User();
        user.setEmail("ayyappa.repaka@gmail.com");
        user.setIsAdmin(true);
        user.setName("Ayyappa");
        user.setPassword("password");
        user.setPhoneNumber("8934719347");
        user.setUserId(123);
        when(userService.updateUser((User) any())).thenReturn(user);

        User user1 = new User();
        user1.setEmail("ayyappa.repaka@gmail.com");
        user1.setIsAdmin(true);
        user1.setName("Ayyappa");
        user1.setPassword("password");
        user1.setPhoneNumber("8934719347");
        user1.setUserId(123);
        String content = (new ObjectMapper()).writeValueAsString(user1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userControllers)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userId\":123,\"password\":\"password\",\"name\":\"Ayyappa\",\"phoneNumber\":\"8934719347\",\"email\":\"ayyappa.repaka@gmail"
                                        + ".com\",\"isAdmin\":true}"));
    }


    @Test
    void testUpdateUser2() throws Exception {
        when(userService.updateUser((User) any())).thenThrow(new UserNotFoundException("?"));

        User user = new User();
        user.setEmail("ayyappa.repaka@gmail.com");
        user.setIsAdmin(true);
        user.setName("Ayyappa");
        user.setPassword("password");
        user.setPhoneNumber("8934719347");
        user.setUserId(123);
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userControllers)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("?"));
    }


    @Test
    void testDeleteAllUser() throws Exception {
        when(userService.deleteAll()).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/deleteAll");
        MockMvcBuilders.standaloneSetup(userControllers)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void testDeleteUser() throws Exception {
        when(userService.deleteUser(anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/delete/{id}", 1);
        MockMvcBuilders.standaloneSetup(userControllers)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void testDeleteUser2() throws Exception {
        when(userService.deleteUser(anyInt())).thenThrow(new UserNotFoundException("?"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/delete/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userControllers)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("?"));
    }


    @Test
    void testDeleteUser3() throws Exception {
        when(userService.deleteUser(anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/user/delete/{id}", 1);
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userControllers)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void testDeleteAllUser2() throws Exception {
        when(userService.deleteAll()).thenReturn(true);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/user/deleteAll");
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userControllers)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void testGetUser() throws Exception {
        User user = new User();
        user.setEmail("ayyappa.repaka@gmail.com");
        user.setIsAdmin(true);
        user.setName("Ayyappa");
        user.setPassword("password");
        user.setPhoneNumber("8934719347");
        user.setUserId(123);
        when(userService.getById(anyInt())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/view/{viewById}", 123);
        MockMvcBuilders.standaloneSetup(userControllers)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userId\":123,\"password\":\"password\",\"name\":\"Ayyappa\",\"phoneNumber\":\"8934719347\",\"email\":\"ayyappa.repaka@gmail"
                                        + ".com\",\"isAdmin\":true}"));
    }


    @Test
    void testGetUserForAuth() throws Exception {
        User user = new User();
        user.setEmail("ayyappa.repaka@gmail.com");
        user.setIsAdmin(true);
        user.setName("Ayyappa");
        user.setPassword("password");
        user.setPhoneNumber("8934719347");
        user.setUserId(123);
        when(userService.getById(anyInt())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/usingforinternalpurpose/{viewById}", 123);
        MockMvcBuilders.standaloneSetup(userControllers)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userId\":123,\"password\":\"password\",\"name\":\"Ayyappa\",\"phoneNumber\":\"8934719347\",\"email\":\"ayyappa.repaka@gmail"
                                        + ".com\",\"isAdmin\":true}"));
    }


    @Test
    void testRegisterUser() throws Exception {
        User user = new User();
        user.setEmail("ayyappa.repaka@gmail.com");
        user.setIsAdmin(true);
        user.setName("Ayyappa");
        user.setPassword("password");
        user.setPhoneNumber("8934719347");
        user.setUserId(123);
        when(userService.saveUser((User) any())).thenReturn(user);

        User user1 = new User();
        user1.setEmail("ayyappa.repaka@gmail.com");
        user1.setIsAdmin(true);
        user1.setName("Ayyappa");
        user1.setPassword("password");
        user1.setPhoneNumber("8934719347");
        user1.setUserId(123);
        String content = (new ObjectMapper()).writeValueAsString(user1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userControllers)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userId\":123,\"password\":\"password\",\"name\":\"Ayyappa\",\"phoneNumber\":\"8934719347\",\"email\":\"ayyappa.repaka@gmail"
                                        + ".com\",\"isAdmin\":true}"));
    }


    @Test
    void testRegisterUser2() throws Exception {
        when(userService.saveUser((User) any())).thenThrow(new UserAlreadyExistException("?"));

        User user = new User();
        user.setEmail("ayyappa.repaka@gmail.com");
        user.setIsAdmin(true);
        user.setName("Ayyappa");
        user.setPassword("password");
        user.setPhoneNumber("8934719347");
        user.setUserId(123);
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userControllers)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(409));
    }
}

