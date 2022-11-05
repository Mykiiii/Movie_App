package com.movie.userservice.service;

import com.movie.userservice.model.User;
import com.movie.userservice.repository.UserRepository;
import com.movie.userservice.util.exception.UserAlreadyExistException;
import com.movie.userservice.util.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(User user) throws UserAlreadyExistException {
        Optional<User> userOptional = userRepository.findById(user.getUserId());

        if(userOptional.isEmpty()){

            return userRepository.save(user);

        }else{
            throw new UserAlreadyExistException("User with id: "+user.getUserId()+" already exists");
        }
    }

    @Override
    public User getById(int id) {

        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get();

        }else{
            return null;
        }

    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException {

        Optional<User> optionalUser = userRepository.findById(user.getUserId());
        if(optionalUser.isEmpty()) throw new UserNotFoundException("User not found");



        return userRepository.saveAndFlush(user);
    }

    @Override
    public boolean deleteUser(int id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) throw new UserNotFoundException("User not found");

        userRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteAll() {

        userRepository.deleteAll();
        return true;

    }

}
