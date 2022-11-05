package com.movieapp.auth.service;

import com.movieapp.auth.model.User;
import com.movieapp.auth.util.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService{

    //@Autowired
    //UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User findByUserIdAndPassword(Integer userId, String password) throws UserNotFoundException {

        User user = restTemplate.getForObject("http://User-Service/user/usingforinternalpurpose/"+userId,User.class);

        if(user == null || !(user.getPassword().equals(password))) return null;

        return user;

    }

}
