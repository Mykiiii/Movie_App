package com.movie.userservice.repository;

import com.movie.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUserIdAndPassword(Integer userId, String password);
}
