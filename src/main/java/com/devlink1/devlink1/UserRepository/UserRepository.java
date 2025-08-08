package com.devlink1.devlink1.UserRepository;

import com.devlink1.devlink1.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);
    User findByEmail(String email);
//    List<User> findByUsernameContainingIgnoreCase(String userName);
}
