package com.movieapp.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class User {


    private int userId;
    private String password;

    private String name;
    private String phoneNumber;
    private String email;
    private Boolean isAdmin;

}
