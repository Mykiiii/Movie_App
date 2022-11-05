package com.movie.userservice.service;

import com.movie.userservice.model.User;
import com.movie.userservice.util.exception.UserAlreadyExistException;
import com.movie.userservice.util.exception.UserNotFoundException;

import java.util.List;

public interface UserService{

    public User saveUser(User user) throws UserAlreadyExistException;

    public User getById(int id);

    public List<User> getAllUser();

    public User updateUser(User user) throws UserNotFoundException;

    public boolean deleteUser(int id) throws UserNotFoundException;

    public boolean deleteAll();

}
