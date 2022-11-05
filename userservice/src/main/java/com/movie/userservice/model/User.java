package com.movie.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
public class User {


    @Id
    private int userId;
    private String password;

    private String name;
    private String phoneNumber;
    private String email;
    private Boolean isAdmin;


}
