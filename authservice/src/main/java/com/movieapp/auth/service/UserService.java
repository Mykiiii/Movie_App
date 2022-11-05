package com.movieapp.auth.service;

import com.movieapp.auth.model.User;
import com.movieapp.auth.util.exception.UserAlreadyExistException;
import com.movieapp.auth.util.exception.UserNotFoundException;

public interface UserService {

    public User findByUserIdAndPassword(Integer userId, String password) throws UserNotFoundException;

 /*   public Boolean saveUser(User user) throws UserAlreadyExistException;*/

}
