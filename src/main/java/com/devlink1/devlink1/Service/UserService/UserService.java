package com.devlink1.devlink1.Service.UserService;

import com.devlink1.devlink1.Entity.User;

import java.util.List;

public interface UserService {

    User addNewUser(User user);

    User findByUsername(String username);

    List<User> getAllUsers();

    User findByEmail(String email);

}
