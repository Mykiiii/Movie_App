package com.movie.userservice.controllers;

import com.movie.userservice.model.User;
import com.movie.userservice.service.UserService;
import com.movie.userservice.util.exception.UserAlreadyExistException;
import com.movie.userservice.util.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserControllers {

    @Autowired
    UserService userService;

    @PostMapping("register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {

            User user1 = userService.saveUser(user);
            return new ResponseEntity<User>(user1,HttpStatus.OK);

        }catch (UserAlreadyExistException e){

            return new ResponseEntity<User>(HttpStatus.CONFLICT);

        }

    }

    @GetMapping("view/{viewById}")
    public ResponseEntity<User> getUser(@PathVariable("viewById") int id){

        User user = userService.getById(id);

        if (user != null) return new ResponseEntity<User>(user,HttpStatus.OK);
        else return new ResponseEntity<User>(user,HttpStatus.CONFLICT);

    }

    @GetMapping("usingforinternalpurpose/{viewById}")
    public ResponseEntity<User> getUserForAuth(@PathVariable("viewById") int id){

        User user = userService.getById(id);

        if (user != null) return new ResponseEntity<User>(user,HttpStatus.OK);
        else return new ResponseEntity<User>(user,HttpStatus.CONFLICT);

    }

    @GetMapping("viewAll")
    public ResponseEntity<?> getAllUser(){

        List<User> userList = userService.getAllUser();

        return new ResponseEntity<>(userList,HttpStatus.OK);

    }

    @PutMapping("update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {

            User user1 = userService.updateUser(user);
            return new ResponseEntity<User>(user1,HttpStatus.OK);

        }catch (UserNotFoundException e){

            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

        }

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        try {

            userService.deleteUser(id);
            return new ResponseEntity<User>(HttpStatus.OK);

        }catch (UserNotFoundException e){

            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

        }

    }

    @DeleteMapping("deleteAll")
    public ResponseEntity<?> deleteAllUser() {

            userService.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);

    }

}
