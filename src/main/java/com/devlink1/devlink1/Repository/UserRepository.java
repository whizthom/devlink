package com.devlink1.devlink1.Repository;

import com.devlink1.devlink1.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);
    User findByEmail(String email);
//    List<User> findByUsernameContainingIgnoreCase(String userName);
}
