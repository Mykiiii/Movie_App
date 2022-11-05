package com.movieapp.auth.controller;

import com.movieapp.auth.model.User;
import com.movieapp.auth.service.UserService;
import com.movieapp.auth.util.exception.UserAlreadyExistException;
import com.movieapp.auth.util.exception.UserNotFoundException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/")
public class UserController {

    private Map<String, String> map = new HashMap<>();

    @Autowired
    UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user) throws ServletException{
        String jwtToken = "";
        try {
            jwtToken = getToken(user.getUserId(), user.getPassword());
            map.clear();
            map.put("message", "Token is generated");
            map.put("token", jwtToken);
        }
        catch(Exception e) {
            String exceptionMsg = e.getMessage();
            map.clear();
            map.put("token", null);
            map.put("message", exceptionMsg);
            //logger.info("In controller - {}", "Unauthorized User.");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        //logger.info("In controller - {}", "Authorized User.");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    public String getToken(Integer userId, String password) throws Exception{
        if(userId == null || password == null) {
            throw new ServletException("Please fill in userId and password.");
        }

        User isUserExists = userService.findByUserIdAndPassword(userId, password);

        if(isUserExists == null ) {
            throw new ServletException("Invalid Credentials.");
        }

        String jwtToken;
        if(isUserExists.getIsAdmin())
        {
            jwtToken = Jwts.builder().setSubject(userId+"'s token.")
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    //.setHeader(mp)
                    //.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                    .signWith(SignatureAlgorithm.HS256, "movieAdminkey")
                    .compact();
            System.out.println("Admin is rolling out");
        }else{
            jwtToken = Jwts.builder().setSubject(userId+"'s token.")
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    //.setHeader(mp)
                    //.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                    .signWith(SignatureAlgorithm.HS256, "moviekey")
                    .compact();
            System.out.println("User is rolling out");
        }

                //logger.info("In controller - {}", "JWT Token created Successfully.");
        return jwtToken;
    }
}
